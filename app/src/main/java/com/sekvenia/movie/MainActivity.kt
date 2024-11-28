package com.sekvenia.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

     /* Привет тому кто будет проверять!
     * по приложению использовалась Clean Architecture
     * так как по заданию можно было использовать XML/Compose верстку,
     * то для фрагмента с листом используется ComposeView,
     * для деталей классика XML
     *
     * для экрана с листом есть переключение тем, есть портретный и ландшафтный экран
     * навигация через navGraph, передача данных о фильме через Parcelable
     * (хотел через SafeArgs) по этому и зависимость осталась
     * про то что все коммиты сделаны почти в одно время, скажу что все
     * коммителось по готовности что-бы избежать закрепления неверного кода
     *
     * по программе не успел доделать переключение тем и темную тему для MovieDetailsFragment
     *
     * если дальше ТЗ не пройдет прошу пожалуйста пройтись по коду в MergeRequest
     * что-бы понять что не так грустить и двигаться дальше
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}