#include <iostream>
#include <vector>
#include <string>
#include <set>
#include <tuple>
#include <algorithm>
#include <fstream>
#include <unordered_map>
#include "dependencies/include/nlohmann/json.hpp"

struct item { // структура для хранения купленных предметов
private:
	std::string id;
	std::string name;

	struct category_class { // структура для хранения к какой категории относится предмет
	private:
		std::string id;
		std::string name;
	public:
		category_class() = delete; // запрещён конструктор по умолчанию
		category_class(const std::string& category_id, const std::string& category_name) : id(category_id), name(category_name) {}; // конструктор, для создания категории

		const std::string& get_category_name() { // функция возвращающая название категории
			return name;
		}
	};
public:
	category_class category;
	item() = delete; // запрещён конструктор по умолчанию
	item(const std::string& item_id, const std::string& item_name, const std::string& category_id, const std::string& category_name) : id(item_id), name(item_name), category(category_id, category_name) {}; // конструктор, который создаёт предметы, которые мы покупаем
};


struct date_time { // струкрура, которая хранит время покупки
private:
	int year;
	int month;
	int day;
	int hour;
	int minute;
	double second;
public:
	date_time() = delete; // запрещён конструктор по умолчанию
	date_time(const int year = 0, const int month = 0, const int day = 0, const int hour = 0, const int minute = 0, const double second = 0) : year(year), month(month), day(day), hour(hour), minute(minute), second(second) {}; // конструктор, для установки времени

	bool operator < (const date_time& b) const { // реалезация оператора (<) для реализации сортировки и бинарного поиска
		return std::tie(year, month, day) < std::tie(b.year, b.month, b.day);
	}

	bool operator > (const date_time& b) const { // реализация оператор (>)
		return b < *this;
	}
};

date_time set_time(const std::string& time) { // функция, которая отвечает за разбиение строки времени на токены и дальнейшее хранение
	std::vector<std::string> tokens;
	int i = 0, j = 0;
	for (i, j; i < time.size(); ++i) {
		if (time[i] == '-' || time[i] == ':' || time[i] == 'T') {
			tokens.push_back(time.substr(j, i - j));
			j = i + 1;
		}
	}
	tokens.push_back(time.substr(j, i - j));
	return date_time(std::stoi(tokens[0]), std::stoi(tokens[1]), std::stoi(tokens[2]), std::stoi(tokens[3]), std::stoi(tokens[4]), std::stod(tokens[5]));
}


struct bought { // структура, которая хранит время покупки и список покупок в этот момент
private:
	date_time time;
	std::vector<item> items;
public:
	bought() = delete; // запрещён конструктор по умолчанию
	bought(const std::string& time) : time(set_time(time)) {}; // конструктор установки времени

	void push(const item& item) { // функция заполнения вектора покупок - покупками
		items.push_back(item);
	}

	date_time& gettime() { // функция, которая возвращает время
		return time;
	}

	std::vector<item>& bought_items() { // функция, которая возвращает вектор покупок
		return items;
	}

	bool operator<(const bought& b) const { // оператор (<) для сортировки
		return this->time < b.time;
	}

	bool operator>(const bought& b) const { // оператор (>) 
		return b.time < this->time;
	}
};

std::set<std::string> find(std::vector<bought>& all_boughts, date_time& left_date, date_time& right_date) { // функция для поиска самых популярных категорий в определённом промежутки времени с left_date по right_date включительно
	int left = -1, right = all_boughts.size();
	while (right - left > 1) { // поиск левой границы, right
		int mid = left + (right - left) / 2;

		if (all_boughts[mid].gettime() < left_date) {
			left = mid;
		}
		else {
			right = mid;
		}
	}
	int left2 = -1, right2 = all_boughts.size();
	while (right2 - left2 > 1) { // поиск правой границы, left2
		int mid = left2 + (right2 - left2) / 2;

		if (all_boughts[mid].gettime() > right_date) {
			right2 = mid;
		}
		else {
			left2 = mid;
		}
	}
	if (left2 == -1 || right == all_boughts.size()) { // если нет ни одной подходящей покупки
		std::cout << "No result\n";
		std::set<std::string> set;
		return set;
	}
	std::unordered_map<std::string, int>  categories_counter; // map для подсчёта количества категорий
	int max = 0;
	for (int i = right; i <= left2; ++i) {
		for (auto items : all_boughts[i].bought_items()) {
			max = std::max(++categories_counter[items.category.get_category_name()], max);

		}
	}
	std::set<std::string> ans;
	for (int i = right; i <= left2; ++i) { // заполнения ans подходящими категориями
		for (auto items : all_boughts[i].bought_items()) {
			if (categories_counter[items.category.get_category_name()] == max) ans.insert(items.category.get_category_name());
		}
	}
	return ans;
}

void print(std::set<std::string>& s) { // функция вывода
	nlohmann::json report;
	report["categories"] = s;
	/*std::ofstream file("report.json"); // Создание файла report.json с ответом
	if (file.is_open()) {
		file << report;
		file.close();
	}*/
	std::cout << report.dump();
}

int main() {
	std::locale::global(std::locale("ru_RU.UTF-8"));

	std::vector<bought> all_boughts; // вектор покупок

	nlohmann::json objJson; // объявляем переменную objJson типа nlohmann::json для хранения JSON данных
	std::fstream fileInput; // объявляем переменную fileInput типа std::fstream для работы с файлом
	fileInput.open("format.json"); // открываем файл с именем "format.json" для чтения
	fileInput >> objJson; // считываем содержимое файла в переменную objJson с помощью оператора >>
	fileInput.close(); // закрываем файл после чтения
	for (auto boughts : objJson) { // заполнение all_boughts
		bought buy(boughts["ordered_at"]);
		for (auto items : boughts["items"]) {
			item item(items["id"], items["name"], items["category"]["id"], items["category"]["name"]);
			buy.push(item);
		}
		all_boughts.push_back(buy);
	}
	std::sort(all_boughts.begin(), all_boughts.end()); // сортировка для бинарного поиска, сделанно, если будет множественные запросы 
	date_time left_date(2023, 12, 1); // левая дата
	date_time right_date(2023, 12, 31); // правая дата
	std::set<std::string> ans;
	ans = find(all_boughts, left_date, right_date); // вызов функции find
	print(ans); // вывод 
}
