## Запуск програми
Запускаємо проєкт за допомогою [**Application.class**](src/main/java/com/github/onlinemovieservice/Application.java)

Щоб запустити програму на H2Database потрібно змінити конфігуряцію на dev, у іншому випадку нічого змінювати не потрібно
(створюючи бд можно змінити port у application.yaml) потрібно створити базу даних на PostgreSQL **16**. У двох випадках
використовується створення та наповнення бд завдяки [**liquidbase**](src/main/resources/db/changelog).

## Опис сутностей
У даному проєкті я використовую як entity1 - Movie.class, а entity2 - Director.class. Додатково створив ще 
entity3 - Genre.class який слугує классом для перевірки багатьох полів для фільму та має зв`язок ManyToMany.

Поля валідуються завдяки @Validated та використовують анотації @NotNull, @NotBlank, @NotEmpty

Приклад інформції для збереження MovieSaveDto
[(файл де можна взяти інфу)](src/main/resources/json/correctFile.json):
```
{
  "title": "TEXT",
  "releaseDate": "yyyy-mm-dd",
  "genresIds": [LongId],
  "directorId": LongId
}
```
Приклад інформації для збереження DirectorSaveDto:
```
{
  "firstName": "TEXT",
  "lastName": "TEXT",
  "nationality": "TEXT"
}
```
Приклад інформації для збереження GenreSaveDto:
```
{
  "name": "TEXT",
}
```
## Виконання запитів _list та _report
### api/movie/_list

Запит для отримання першої сторінки розміром 10 сутностей(entity2)
```
{
  "page": "0"
  "size": "10"
}
```
Завдяки Specification(Director) ми можемо виконувати динамічну фільтрацію
```
{
  "lastName": "Nolan"
  "page": "0"
  "size": "10"
}
```
або
```
{
  "lastName": "Nolan"
  "nationality": "British"
  "page": "0"
  "size": "10"
}
```
Ми можемо робити фільтрацію від 0 до кількості полів у Director(3)

### api/movie/_report

Ми маємо ту ж саму логіку фільтрації але використовуюємо без Pageable та отримуємо СSV. Запит має такий вигляд:
```
{
  "lastName": "Nolan"
  "nationality": "British"
}
```
або
```
{
  "nationality": "British"
}
```