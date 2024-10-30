package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

/**
 * [Datasource] generates a list of [Affirmation]
 */
class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.ima1),
            Affirmation(R.string.affirmation2, R.drawable.ima2),
            Affirmation(R.string.affirmation3, R.drawable.ima3),
            Affirmation(R.string.affirmation4, R.drawable.ima4),
            Affirmation(R.string.affirmation5, R.drawable.ima5),
            Affirmation(R.string.affirmation6, R.drawable.ima6))
    }
}
