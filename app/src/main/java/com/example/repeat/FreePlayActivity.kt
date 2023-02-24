package com.example.repeat

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View

class FreePlayActivity : GameActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_free_play)
    }

    override fun onClickCat(view: View) {
        playCat(view)
    }

    override fun onClickClick(view: View) {
        playClick(view)
    }

    override fun onDrumClick(view: View) {
        playDrum(view)
    }

    override fun onPianoClick(view: View) {
        playPiano(view)
    }
}