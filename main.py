import json
from collections import Counter
from datetime import datetime

DATE_FORMAT = "%Y-%m-%dT%H:%M:%S.%f"


def check_date(date: datetime) -> bool:
    return date.month == 12


def find_pop_cats(data: list[dict]) -> str:
    if not data:
        return r"{}"

    cats_counter = Counter()
    cats_dct = {}
    for order in data:
        order_date = datetime.strptime(order["ordered_at"], DATE_FORMAT)

        if not check_date(order_date):
            continue

        for item in order["items"]:
            cat_id = item["category"]["id"]
            cats_counter[cat_id] += 1
            cat_name = item["category"]["name"]
            cats_dct[cat_id] = cat_name

    max_cnt = max(cats_counter.values())
    pop_cats = [
        cats_dct[cat_id] for cat_id in cats_counter if cats_counter[cat_id] == max_cnt
    ]

    result = {"categories": sorted(pop_cats)}
    return json.dumps(result, ensure_ascii=False)


def find_pop_cats_in_file(filename: str) -> str:
    try:
        with open(filename, "r", encoding="utf-8") as f:
            data = json.load(f)
    except FileNotFoundError:
        print(
            "Файл не найден. Вероятно, программа и файл лежат в разных папках. Переместите их в одну папку и запустите программу снова."
        )
        exit()

    return find_pop_cats(data)


# Test the function
filename = "input.json"
result = find_pop_cats_in_file(filename)
print(result)
