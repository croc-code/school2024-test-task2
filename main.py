import json
from typing import Dict, Any, List


# функия для проверки того, что заказ был совершен в декабре
def is_december_date(date: str) -> bool:
    month = date.split("-", 2)[1]
    if month == "12":
        return True
    else:
        return False

# функция получения данных из json-файла
def get_json_data(file_title: str) -> Dict[str, Any]:
    with open(file_title, encoding="utf8") as f:
        data = json.load(f)
        return data

# функция для получения словаря, ключом которого является название категории,
# a значением - количество совершенных покупок из данной категории
def get_product_categories_stats(orders_data: Dict[str, int]) -> Dict[str, Any]:
    categories_stats = {}
    for order in orders_data:
        order_date = order["ordered_at"]
        if is_december_date(order_date): # проверяем, что заказ был совершен в предновогодний преиод
            shop_basket = order["items"]
            for product in shop_basket:
                category = product["category"]
                category_name = category["name"]
                if category_name not in categories_stats:
                    categories_stats[category_name] = 0
                categories_stats[category_name] += 1
    return categories_stats

# функция получения списка наиболее востребованных категорий
def get_most_popular_categories(categories_stats: Dict[str, int]) -> List[str]:
    most_popular_categories_value = max(categories_stats.values()) # определяем кол-во покупок, совершенной в одной из самых востребованных категорий товаров
    most_popular_categories = []
    for category in categories_stats:
        category_value = categories_stats[category]
        if category_value == most_popular_categories_value:
            most_popular_categories.append(category)
    most_popular_categories.sort()
    return most_popular_categories

# процедура для записи отчета в выходной файл "output_data"
def get_report_json_data(most_popular_categories: List[str]):
    report_data = {
        "categories": most_popular_categories
    }
    with open("output_data.json", "w", encoding="utf8") as f:
        json.dump(report_data, f, indent=4, ensure_ascii=False)

def main():
    file_title = input("Введите название входного файла для чтения данных: ")
    orders_data = get_json_data(file_title=file_title)
    categories_stats = get_product_categories_stats(orders_data=orders_data)
    most_popular_categories = get_most_popular_categories(categories_stats=categories_stats)
    get_report_json_data(most_popular_categories=most_popular_categories)


if __name__ == "__main__":
    main()
