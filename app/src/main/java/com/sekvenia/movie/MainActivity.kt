package com.sekvenia.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.sekvenia.movie.navigation.Screens
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

     /**
     *1. Заголовок Фильмы находит не по центру экрана. - ГОТОВО
     *2. Индикатор загрузки не соответствует дизайну. - ГОТОВО
     *3. Цвет статус бара не соответствует дизайну. - ГОТОВО
     *4. Заглушка для отсутствия изображения не соответствует дизайну. - ГОТОВО
     *
     *5. Не все отступы, закругления, цвета, размеры на экранах соответствуют дизайну.
     *Например, нет закругления у изображения фильма, отступ по вертикали между жанрами,
     *отступ названия фильма от изображения на экране списка фильмов,
     *нет отступа слева и снизу у описания фильма на экране детальной информации о фильме и т.д.
     *
     *6. Для некоторых фильмов приходят невалидные данные.
     *Например, осуждение и !!!!убийство Жана Поля Марата!!!. При переходе к этому фильму приложение падает.
     **- частично, nullable поля установлены
     *
     *7. Сбрасывается выбранный жанр при возвращении на
     *экран списка фильмов с экрана детальной информации о фильме или смене ориентации экрана. - ГОТОВО
     *
     *8. Из описания задания: При повторном клике на жанр необходимо снять выделение с жанра
     *   и показать весь список фильмов. При повторном клике жанр остается выбранным. - ГОТОВО
     *
     *9. В горизонтальной ориентации экран списка фильмов не соответствует дизайну. - ГОТОВО
     *
     *
     *- в MovieListP сделать LazyCollum
     *- жанры с большой буквы - ГОТОВО
     *- попилить на функции фрагменты
     *- переезд навигации на cicerone - ГОТОВО
     *- разобрать data class на data и domain - ГОТОВО - перепроверить
     *- обьединить темы compose и xml
     *- многомодульность
     *- написать тесты
     **/

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()
    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Movies)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.replaceScreen(Screens.movieList())
        }
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}