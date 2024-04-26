import json
import os
from typing import List, Dict


def generate_report(valid_data: List[Dict]) -> str:
    """
    Генерирует отчет о популярных категориях товаров на основе предоставленных данных в предновогодний период.

    Args:
    valid_data (List[Dict]): Список словарей с данными о заказах и товарами.

    Returns:
    str: JSON-строка с отчетом о популярных категориях товаров в предновогодний период.
    """
    # Формируем список товаров, заказанных в предновогодний период
    pre_new_year_items = []
    for order in valid_data:
        if order.get("ordered_at", "")[5:7] == "12":
            pre_new_year_items.extend(order["items"])

    # Подсчитываем количество товаров в каждой категории
    categories_count = {}
    for item in pre_new_year_items:
        category_name = item["category"]["name"]
        categories_count[category_name] = categories_count.get(category_name, 0) + 1

    # Определяем популярные категории
    if categories_count:
        max_count = max(categories_count.values())
        popular_categories = sorted([category for category, count in categories_count.items() if count == max_count])
    else:
        popular_categories = []

    # Генерируем отчет в формате JSON
    report_data = {"categories": popular_categories}
    report_json = json.dumps(report_data, ensure_ascii=False)

    return report_json


def check_json_file(file_path: str) -> List[Dict]:
    """
    Проверяет JSON-файл на соответствие необходимому формату и возвращает данные из файла.

    Args:
    file_path (str): Путь к JSON-файлу.

    Returns:
    List[Dict]: Список словарей с данными о заказах и товарами в случае соответствия формату, иначе возвращает False.
    """
    # Проверяем формат JSON-файла и возвращаем данные
    with open(file_path, 'r', encoding='utf-8') as file:
        received_data = json.load(file)

    # Проверяем соответствие структуры данных требуемому формату
    for order in received_data:
        if "ordered_at" not in order or "items" not in order:
            return False
        for item in order["items"]:
            if "id" not in item or "name" not in item or "category" not in item:
                return False
            if "id" not in item["category"] or "name" not in item["category"]:
                return False

    return received_data


# Определяем текущий рабочий каталог
current_directory = os.getcwd()
input_json_file = os.path.join(current_directory, "input.json")

# Проверяем наличие и соответствие JSON-файла
if os.path.exists(input_json_file):
    if data := check_json_file(input_json_file):
        report = generate_report(data)
        print(report)
    else:
        print("Файл input.json не соответствует необходимому шаблону.")
else:
    print("Файл input.json не найден в текущем рабочем каталоге.")
