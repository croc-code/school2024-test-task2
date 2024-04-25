import json
import datetime as dt

def get_json(file):
    with open(file, encoding='utf8') as f:
        template = json.load(f)

    category = {}
    max_app = -1

    for i in template:
        d = int(i['ordered_at'].split('T')[0].split('-')[-1])
        m = int(i['ordered_at'].split('T')[0].split('-')[-2])
        y = int(i['ordered_at'].split('T')[0].split('-')[-3])
        data = dt.date(y, m, d).strftime('%d.%m.%Y')

        if dt.datetime(2023, 12, 1).strftime('%d.%m.%Y') <= data <= dt.datetime(2023, 12, 31).strftime('%d.%m.%Y'):
            for j in i['items']:
                if j['category']['name'] not in category:
                    category[j['category']['name']] = 0
                category[j['category']['name']] += 1
                if category[j['category']['name']] > max_app:
                    max_app = category[j['category']['name']]

    res_list = []
    if len(category) != 0:
        for i in category:
            if category[i] == max_app:
                res_list.append(i)
    res_list.sort()
    to_json = {'categories': res_list}
    return to_json

print(get_json('input.json'))
