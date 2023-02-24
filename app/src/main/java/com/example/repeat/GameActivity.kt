package com.example.repeat

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

public open class GameActivity : Activity() {

    var countClick: Int = 0

    private var prefs: Preference? = null
    private var playerCat: MediaPlayer? = null
    private var playerClick: MediaPlayer? = null
    private var playerDrum: MediaPlayer? = null
    private var playerPiano: MediaPlayer? = null
    private var listSeqMain: MutableList<MediaPlayer?> = MutableList(0) { null }
    private var listSeqUser: MutableList<MediaPlayer?> = MutableList(0) { null }

    var listSounds: List<MediaPlayer?> = listOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        prefs = Preference(this)
        playerCat = MediaPlayer.create(this, R.raw.cat)
        playerClick = MediaPlayer.create(this, R.raw.click)
        playerDrum = MediaPlayer.create(this, R.raw.drum)
        playerPiano = MediaPlayer.create(this, R.raw.piano)
        listSounds = listOf(playerCat, playerClick, playerDrum, playerPiano)

        restartGame()
    }

    open fun onClickCat(view: View) {
        playCat(view)
        listSeqUser.add(playerCat)
        checkFinish(listSeqUser[countClick])
    }

    open fun onClickClick(view: View) {
        playClick(view)
        listSeqUser.add(playerClick)
        checkFinish(listSeqUser[countClick])
    }

    open fun onDrumClick(view: View) {
        playDrum(view)
        listSeqUser.add(playerDrum)
        checkFinish(listSeqUser[countClick])
    }

    open fun onPianoClick(view: View) {
        playPiano(view)
        listSeqUser.add(playerPiano)
        checkFinish(listSeqUser[countClick])
    }

    fun playCat(view: View) {
        playerCat?.start()
        switchColor(findViewById<Button>(R.id.buttonCat), Color.BLACK)

    }

    fun playClick(view: View) {
        playerClick?.start()
        switchColor(findViewById<Button>(R.id.buttonClick), Color.GREEN)
    }

    fun playDrum(view: View) {
        playerDrum?.start()
        switchColor(findViewById<Button>(R.id.buttonDrum), Color.RED)
    }

    fun playPiano(view: View) {
        playerPiano?.start()
        switchColor(findViewById<Button>(R.id.buttonPiano), Color.BLUE)
    }

    fun backToMenu(view: View) {
        val intent = Intent(this@GameActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun switchColor(button: Button, color: Int) {
        button.setBackgroundColor(Color.WHITE)
        Executors.newSingleThreadScheduledExecutor().schedule({
            button.setBackgroundColor(color)
        }, 1, TimeUnit.SECONDS)
    }

    private fun restartGame() {
        listSeqUser.clear()
        listSeqMain.clear()
        listSeqMain.add(listSounds.random())
        updateLevels()
    }

    private fun errorAlert() {
        val builderError = AlertDialog.Builder(this)

        builderError.setTitle("Wrong Button!")
        builderError.setMessage("Level: ${listSeqMain.size - 1}")
        builderError.setPositiveButton(
            "Restart",
            DialogInterface.OnClickListener { dialogInterface, i ->
                restartGame()
            })
        builderError.setNegativeButton(
            "Menu",
            DialogInterface.OnClickListener { dialogInterface, i ->
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
            }
        )
        builderError.show()
    }

    fun onClickStart(view: View) {
        playSeq(view, listSeqMain)
    }

    private fun updateLevels() {
        findViewById<TextView>(R.id.currentLevel).setText("Current level: ${listSeqMain.size}")
        findViewById<TextView>(R.id.maxLevel).setText("Max level: ${prefs!!.getMaxLevel()}")
    }

    private fun playSeq(view: View, list: MutableList<MediaPlayer?>) {
        val timer = Timer()
        var index = 0
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (index < listSeqMain.size) {
                        when (listSeqMain[index++]) {
                            playerCat -> playCat(view)
                            playerDrum -> playDrum(view)
                            playerClick -> playClick(view)
                            playerPiano -> playPiano(view)
                        }
                    } else timer.cancel()

                }
            }
        }, 0, 1000)

        updateLevels()

    }

    private fun checkFinish(el: MediaPlayer?) {
        if (el != listSeqMain[countClick] || countClick > (listSeqMain.size - 1)) {
            countClick = 0
            Log.e("ERRor", "error")
            errorAlert()
        } else if (el == listSeqMain[countClick] && countClick == (listSeqMain.size - 1)) {
            countClick = 0
            Log.e("Nice: ", "Nice")
            if (listSeqMain.size > prefs!!.getMaxLevel()) prefs!!.updateMax(listSeqMain.size)
            listSeqMain.add(listSounds.random())
            listSeqUser.clear()
            updateLevels()
        } else countClick++
    }

}