Задача 1. Создать SearchViewModel

1. Создать `sealed class SearchState` с четырьмя состояниями поиска
2. Создать `Creator` для внедрения репозитория как зависимость
3. Создать `SearchViewModel` с `Factory`, функцией поиска, состоянием `SearchState`

Задача 2. Создать SearchScreen

1. Добавить передачу `SearchViewModel` в `SearchScreen` через `PlaylistHost`
2. Привязать `SearchViewModel` к существующему `SearchScreen`
3. Сверстать список треков по макету Figma
4. Добавить обработку всех состояний `SearchState`