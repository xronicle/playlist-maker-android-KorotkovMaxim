## Задача 1. Создать SearchViewModel

- [x] Создать `sealed class SearchState` с четырьмя состояниями поиска
- [x] Создать `Creator` для внедрения репозитория как зависимость
- [x] Создать `SearchViewModel` с `Factory`, функцией поиска, состоянием `SearchState`

## Задача 2. Создать SearchScreen

- [x] Добавить передачу `SearchViewModel` в `SearchScreen` через `PlaylistHost`
- [x] Привязать `SearchViewModel` к существующему `SearchScreen`
- [x] Сверстать список треков по макету Figma
- [x] Добавить обработку всех состояний `SearchState`, а также вызов функции поиска при нажатии соответствующей кнопки
