package com.example.diplom2022.views.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.databinding.FragmentTestBinding
import com.example.diplom2022.viewmodels.ApplicationViewModel
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }
    private lateinit var questions: ArrayList<Question>

    private var correctAnswers: Int = 0

    private var isFirstInit: Boolean = true

    private lateinit var currentQuestion: Question

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        questions = ArrayList(mutableListOf())
        initTest()
        val checkAnswerBtn = root.findViewById(R.id.checkAnswerBtn) as Button

        checkAnswerBtn.setOnClickListener { onClickListener() }
        return root
    }

    private fun initTest() {
        val testId = ApplicationConfig.selectedTestId.value
        applicationViewModel.tests.observe(viewLifecycleOwner) {
            for (el in it)
                if (el.id == testId)
                    titleTestTextView.text = el.title
        }
        applicationViewModel.questions.observe(viewLifecycleOwner) { questions ->
            for (el in questions) {
                if (el.testId == testId) {
                    this.questions.add(el)
                    questionTextView.text = el.title.substring(2)
                }
            }
            currentQuestion = this.questions[0]
            isFirstInit = false
            initQuestion()
        }
    }

    private fun initQuestion() {
        answerBtn1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        answerBtn2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        answerBtn3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        answerBtn1.text = currentQuestion.answerA
        answerBtn2.text = currentQuestion.answerB
        answerBtn3.text = currentQuestion.answerC
        questionTextView.text = currentQuestion.title
        radioGroup.clearCheck()
        lockingRadio(true)

        checkAnswerBtn.setText(R.string.checkAnswerText)
    }

    private fun onClickListener() {
        if (radioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(
                context,
                getString(R.string.testInstructionText),
                Toast.LENGTH_SHORT,
            ).show()
            return
        }
        if (checkAnswerBtn.text == getString(R.string.checkAnswerText)) {
            checkAnswer()
        } else {
            if (questions.size < 2) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Тест окончен")
                    .setMessage("Вы завершили тест ответив правильно на $correctAnswers из 3 вопросов")
                    .setPositiveButton("Продолжить") { dialog, _ ->
                        this.fragmentManager?.popBackStack()
                        dialog.cancel()
                    }
                builder.create().show()
            } else {
                questions.removeAt(0)
                currentQuestion = questions[0]
                initQuestion()
            }
        }
    }

    private fun checkAnswer() {
        val checkedBtn: RadioButton = requireView().findViewById(radioGroup.checkedRadioButtonId)
        lockingRadio(false)

        if (isAnswerCorrect(checkedBtn)) {
            correctAnswers++
            checkedBtn.isEnabled = true
            checkedBtn.setTextColor(ContextCompat.getColor(requireContext(),R.color.Green))
        } else {
            showTrueAnswer()
            checkedBtn.isActivated = true
            checkedBtn.setTextColor(ContextCompat.getColor(requireContext(),R.color.Red))
        }

        checkAnswerBtn.text = getString(R.string.nextQuestionText)
        animationBtnNext()
    }

    private fun showTrueAnswer() {
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            if (isAnswerCorrect(radioButton)) {
                radioButton.isEnabled = true
                radioButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.Green))
            }
        }
    }

    private fun animationBtnNext() {
        val animAlpha = AnimationUtils.loadAnimation(
            checkAnswerBtn.context,
            androidx.appcompat.R.anim.abc_fade_in
        )
        view?.startAnimation(animAlpha)
    }

    private fun isAnswerCorrect(answerChecked: RadioButton): Boolean {
        val answer = answerChecked.text.toString()
        return answer == getEqualAnswer()
    }

    private fun getEqualAnswer(): String {
        when (currentQuestion.correctAnswer) {
            1 -> return currentQuestion.answerA
            2 -> return currentQuestion.answerB
            3 -> return currentQuestion.answerC
        }
        return ""
    }

    private fun lockingRadio(choice: Boolean) {
        radioGroup.getChildAt(0).isClickable = choice
        radioGroup.getChildAt(1).isClickable = choice
        radioGroup.getChildAt(2).isClickable = choice
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}