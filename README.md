# Тестовое задание для отбора на Летнюю ИТ-школу КРОК по разработке

## Условие задания
Один развивающийся и перспективный маркетплейс активно растет в настоящее время. Текущая команда разработки вовсю занята тем, что развивает ядро системы. Помимо этого, перед CTO маркетплейса стоит задача — разработать подсистему аналитики, которая на основе накопленных данных формировала бы разнообразные отчеты и статистику.

Вы — компания подрядчик, с которой маркетплейс заключил рамочный договор на выполнение работ по разработке этой подсистемы. В рамках первого этапа вы условились провести работы по прототипированию и определению целевого технологического стека и общих подходов к разработке.

На одном из совещаний с Заказчиком вы определили задачу, на которой будете выполнять работы по прототипированию. В качестве такой задачи была выбрана разработка отчета о наиболее популярных категориях товаров, продаваемых во время подготовки новогодних подарков покупателями.

Аналитики со стороны маркетплейса предоставили небольшой срез массива данных (файл format.json) о совершенных покупках пользователей за 4-й квартал 2023 года, на примере которого вы смогли бы ознакомиться с форматом входных данных. Каждая запись данного среза содержит следующую информацию:
- Дата и время оформления заказа;
- Корзина.

В пояснительной записке к массиву данных была уточняющая информация относительно формате данных, представленных в корзине. Так, например, корзина - это массив однотипных сведений о купленных товаров, определяемых следующим набором данных:
- Идентификатор товара;
- Наименование товара;
- Категория.

В свою очередь сведения о категории представлены в следующем виде:
- Идентификатор категории;
- Наименование категории.

Необходимо разработать отчет, определяющий категории товаров, наиболее популярные перед Новым годом. Если популярных категорий товаров больше, чем одна, отчет должен показывать их все. Предновогодним периодом будем считать срок с 1 по 31 декабря.

Требования к реализации:
1. Реализация должна содержать, как минимум, одну процедуру (функцию/метод), отвечающую за формирование отчета, и должна быть описана в readme.md в соответствии с чек-листом;
2. В качестве входных данных программа использует json-файл (input.json), соответствующий структуре, описанной в условиях задания;
3. Процедура (функция/метод) формирования отчета должна возвращать строку в формате json следующего формата:
   - {«categories»: [«Бытовая техника»]}
   - {«categories»: [«Бытовая техника», «Косметика»]}
4. Найденная в соответствии с условием задачи категория должна выводиться в изначальном наименовании, приведенном в файле с входными данными. Если таких категорий несколько, то на вывод они все подаются в алфавитном порядке.

## Автор решения
Семенов Марсель Евгеньевич
## Описание реализации
Решение предоставлено на языке Java. 

Используемые библиотеки: Lombok, Jackson. 

В коде будет описано всё также как и ниже, только в виде комментариев.

Первым делом создаю папку data, куда помещаю файл format.json из которого будут браться данные.

![326177168-a384c75a-1207-4673-a37f-522150965161](https://github.com/slattchrome/school2024-test-task2/assets/112937058/6e324d07-101c-4698-943e-22c58b297fab)

Далее создаю класс Category, который отвечает за категорию нашего товара.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/5628d57e-2b3f-4ee0-87ab-862306cd47b0)

Затем создаю класс Item, в котором оглавляются поля отвечающие за объект покупаемого продукта.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/c4e45cf0-dfe8-41ca-a8b6-c325dacf35c5)

И наконец создаю класс полноценного заказа Order, который содержит только дату заказа и список покупок.
(Аннотоация @JsonProperty указывается для того, чтобы библиотека распознала это поле, потому что его название в моём классе не соответствует названию в json файле)

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/3fb96aab-3719-4827-991c-a1f114a8b317)

Затем создаю класс DataAnalytics. В нём я создаю метод getAnalytics() который будет выполнять всю логику определения наиболее востребованных категорий. Инциализрую переменную в которой содержится путь до файла с анализируемыми данными и также создаю объект класса ObjectMapper чтобы прочитать json файл. 

![326177800-cf2762f9-1f8e-41f3-92fc-04141cfc9c7b](https://github.com/slattchrome/school2024-test-task2/assets/112937058/62e85ad7-5459-4c41-83af-26a5448b0db3)

Создаю список с заказами куда и записываю их пока читаю файл.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/9ef715d5-0e4b-4676-a52b-60e631c79c4e)

Далее создаю мапу в которой будут храниться названия категорий и количество их появлений в заказах.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/7bc6bbfd-4a79-43fa-af17-9f419809c553)

После создаю цикл который который проходится по каждому заказу, в свою очередь в нём цикл который проходится по каждой позиции из заказа, берём из позиции имя категории и записываем в мапу, а значение ставим количество появлений в заказах.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/3974243e-d332-44ed-8caf-319f4afe0127)

Теперь создаю переменную которая будет содержать значение самой востребованной категории и создаю цикл, который будет переберая значения из нашей мапы, искать наибольшее.  

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/f168f4a6-b0c7-4b98-9317-9050f8cd90fb)

Потом создаю список, куда буду помещать названия самых востребованных категорий и после через цикл сравниваю наибольшее значение со значениями из мапы. Добавляю самые востребованные категории в список.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/056e58f2-1c60-4b66-a514-0a7b66cf94ed)

В конце концов создаю переменнцю в которую записываю полученные названия месяцев в соответствии с требованиями и возвращаю её.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/991ff520-b959-4d70-aca1-026dfb216c10)

Метод написан. Перехожу в класс Main чтобы проверить что всё работает. Создаю экземпляр класса DataAnalytics, чтобы получить доступ к методу getAnalytics() и затем вывожу результат в консоль.

![изображение](https://github.com/slattchrome/school2024-test-task2/assets/112937058/8d37ec5c-dafa-4d9d-9301-3fb94d161156)


## Инструкция по сборке и запуску решения
1. Открыть проект в вашей среде разработки
2. Перейти в натройки компиляторя(в моём случае Intelij IDEA), перейти в раздел Compiler -> Annotation Processor, поставить галочку в поле Enable annotation processing и нажать Apply
   
   ![326212433-89a8fca1-b07c-4740-88ff-e5a3dbfa7f6f](https://github.com/slattchrome/school2024-test-task2/assets/112937058/376889ac-7ece-43d5-9dd0-10f1d982543a)

4. Откройте папку src и перейдите в класс Main
5. Запустите код и наблюдайте вывод в консоли
   
**ВАЖНО: чтобы работать с другими данными замените содержимоет файла format.json, который находится в папке data!**
