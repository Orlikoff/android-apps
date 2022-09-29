package com.orlik.calculator.fragments

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.orlik.calculator.databinding.FragmentSimpleNumpadBinding
import org.mariuszgromada.math.mxparser.*
import kotlin.math.acos
import kotlin.math.tan

class SimpleNumpadFragment : Fragment() {

    private lateinit var _binding: FragmentSimpleNumpadBinding
    private val binding get() = _binding

    companion object {
        lateinit var field: EditText
        lateinit var display: TextView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // binding setup
        _binding = FragmentSimpleNumpadBinding.inflate(inflater, container, false)

        // first setup
        mXparser.disableAlmostIntRounding()
        field.setSelection(1)

        // setting up the basic buttons
        binding.zero.setOnClickListener { zeroBtnPush() }
        binding.one.setOnClickListener { oneBtnPush() }
        binding.two.setOnClickListener { twoBtnPush() }
        binding.three.setOnClickListener { threeBtnPush() }
        binding.four.setOnClickListener { fourBtnPush() }
        binding.five.setOnClickListener { fiveBtnPush() }
        binding.six.setOnClickListener { sixBtnPush() }
        binding.seven.setOnClickListener { sevenBtnPush() }
        binding.eight.setOnClickListener { eightBtnPush() }
        binding.nine.setOnClickListener { nineBtnPush() }
        binding.openBracket.setOnClickListener { openBracketBtnPush() }
        binding.closeBracket.setOnClickListener { closeBracketBtnPush() }
        binding.dot.setOnClickListener { dotBtnPush() }
        binding.divide.setOnClickListener { divideBtnPush() }
        binding.multiply.setOnClickListener { multiplyBtnPush() }
        binding.subtract.setOnClickListener { subtractBtnPush() }
        binding.add.setOnClickListener { addBtnPush() }
        binding.clearOne.setOnClickListener { clearOneBtnPush() }
        binding.clearAll.setOnClickListener { clearAllBtnPush() }
        binding.evaluate.setOnClickListener { evaluateBtnPush() }

        // setting up the PRO buttons
        binding.sin?.setOnClickListener { sinBtnPush() }
        binding.cos?.setOnClickListener { cosBtnPush() }
        binding.tan?.setOnClickListener { tanBtnPush() }
        binding.arcSin?.setOnClickListener { asinBtnPush() }
        binding.arcCos?.setOnClickListener { acosBtnPush() }
        binding.arcTan?.setOnClickListener { atanBtnPush() }
        binding.log?.setOnClickListener { logBtnPush() }
        binding.ln?.setOnClickListener { lnBtnPush() }
        binding.sqrt?.setOnClickListener { sqrtBtnPush() }
        binding.e?.setOnClickListener { eBtnPush() }
        binding.pi?.setOnClickListener { piBtnPush() }
        binding.abs?.setOnClickListener { absBtnPush() }
        binding.prime?.setOnClickListener { primeBtnPush() }
        binding.xSecond?.setOnClickListener { xSecondBtnPush() }
        binding.xYth?.setOnClickListener { xYthBtnPush() }

        return binding.root
    }

    private fun updateDisplay(string: String) {
        var cursorPosition = field.selectionStart
        var oldString = field.text.toString()

        if (oldString == "0" && string != ".") {
            oldString = ""
            cursorPosition = 0
        }

        if (oldString.contains(".") && string == ".") return

        val leftString = oldString.substring(0, cursorPosition)
        val rightString = oldString.substring(cursorPosition)

        field.setText(String.format("%s%s%s", leftString, string, rightString))
        field.setSelection(cursorPosition + string.length)
    }

    private fun zeroBtnPush() = updateDisplay("0")
    private fun oneBtnPush() = updateDisplay("1")
    private fun twoBtnPush() = updateDisplay("2")
    private fun threeBtnPush() = updateDisplay("3")
    private fun fourBtnPush() = updateDisplay("4")
    private fun fiveBtnPush() = updateDisplay("5")
    private fun sixBtnPush() = updateDisplay("6")
    private fun sevenBtnPush() = updateDisplay("7")
    private fun eightBtnPush() = updateDisplay("8")
    private fun nineBtnPush() = updateDisplay("9")
    private fun openBracketBtnPush() = updateDisplay("(")
    private fun closeBracketBtnPush() = updateDisplay(")")
    private fun dotBtnPush() = updateDisplay(".")
    private fun divideBtnPush() = updateDisplay("/")
    private fun multiplyBtnPush() = updateDisplay("*")
    private fun subtractBtnPush() = updateDisplay("-")
    private fun addBtnPush() = updateDisplay("+")
    private fun sinBtnPush() = updateDisplay("sin(")
    private fun cosBtnPush() = updateDisplay("cos(")
    private fun tanBtnPush() = updateDisplay("tan(")
    private fun asinBtnPush() = updateDisplay("asin(")
    private fun acosBtnPush() = updateDisplay("acos(")
    private fun atanBtnPush() = updateDisplay("atan(")
    private fun logBtnPush() = updateDisplay("log10(")
    private fun lnBtnPush() = updateDisplay("ln(")
    private fun sqrtBtnPush() = updateDisplay("sqrt(")
    private fun eBtnPush() = updateDisplay("e")
    private fun piBtnPush() = updateDisplay("pi")
    private fun absBtnPush() = updateDisplay("abs(")
    private fun primeBtnPush() = updateDisplay("ispr(")
    private fun xSecondBtnPush() = updateDisplay("^(2)")
    private fun xYthBtnPush() = updateDisplay("^(")

    private fun clearOneBtnPush() {
        val cursorPosition = field.selectionStart
        val fieldLength = field.text.length

        if (cursorPosition == 0 || fieldLength == 0) return

        val selection: SpannableStringBuilder = field.text as SpannableStringBuilder
        selection.replace(cursorPosition - 1, cursorPosition, "")

        if (selection.isEmpty()) {
            selection.append("0")
            field.text = selection
            field.setSelection(1)
        } else {
            field.text = selection
            field.setSelection(cursorPosition - 1)
        }
    }

    private fun clearAllBtnPush() {
        field.setText("0")
        field.setSelection(1)
    }

    private fun evaluateBtnPush() {
        val userExpression = field.text.toString()

        val exp = Expression(userExpression)
        val result = exp.calculate().toString()

        if (userExpression.contains("E")) Toast.makeText(
            requireContext(),
            "Operation with scientific notation, result might be inaccurate",
            Toast.LENGTH_SHORT
        ).show()
        else if (result.contains("E")) Toast.makeText(
            requireContext(),
            "Maximum precision exceeded, result might be inaccurate",
            Toast.LENGTH_SHORT
        ).show()

        field.setText(result)
        field.setSelection(result.length)

        display.text = result
    }

}