package jp.s64.android.example.favsgma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import local.jp.s64.android.example.favsgma.core.MyCoreWrapper
import jp.s64.android.example.favsgma.databinding.MyActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import local.jp.s64.android.example.favsgma.core.flavor

class MyActivity : AppCompatActivity() {

    companion object {

        private const val UNIT_ID = "ca-app-pub-3940256099942544/5224354917"

    }

    private lateinit var binding: MyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.my_activity)

        binding.status = "Load"
        binding.coreFlavor = MyCoreWrapper.flavor()

        binding.button.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                binding.status = "Loading..."
                val rv = MyCoreWrapper.newRv(this@MyActivity)
                if (rv.load(UNIT_ID)) {
                    if (rv.show()) {
                        binding.status = "Completed"
                    } else {
                        binding.status = "Show error"
                    }
                } else {
                    binding.status = "Load error"
                }
            }
        }
    }

}