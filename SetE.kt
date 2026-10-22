import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class PaymentViewModel : ViewModel() {

    var message by mutableStateOf("")
    var error by mutableStateOf("")

    private val handler = CoroutineExceptionHandler { _, e ->
        error = e.message ?: "Payment Failed"
        message = "Payment Failed"
    }

    fun processPayment() {
        viewModelScope.launch(handler) {
            message = "Processing Payment"
            error = ""

            delay(2000)

            throw Exception("Network Error")
        }
    }
}

@Composable
fun PaymentScreen(vm: PaymentViewModel) {

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(text = vm.message)

        Button(onClick = { vm.processPayment() }) {
            Text("Pay Now")
        }

        if (vm.error.isNotEmpty()) {
            Card {
                Text(
                    text = "Payment Error: ${vm.error}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
