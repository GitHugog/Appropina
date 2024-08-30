package gutierrez.padilla.appropina

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajustar padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del botón de cálculo
        val calculateButton: Button = findViewById(R.id.calculate_button)
        calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        // Obtener el costo del servicio
        val costOfServiceField: EditText = findViewById(R.id.cost_of_service)
        val cost = costOfServiceField.text.toString().toDoubleOrNull()

        // Verificar si el costo es nulo o cero
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        // Determinar el porcentaje de la propina
        val tipPercentage = when (findViewById<RadioGroup>(R.id.tip_options).checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calcular la propina
        var tip = cost * tipPercentage

        // Verificar si se debe redondear
        val roundUpSwitch: Switch = findViewById(R.id.round_up_switch)
        if (roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // Mostrar la propina
        displayTip(tip)
    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val tipResult: TextView = findViewById(R.id.tip_result)
        tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}
