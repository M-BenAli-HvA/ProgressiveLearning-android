package com.example.progressivelearning_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.progressivelearning_android.R
import com.example.progressivelearning_android.model.LearningGoal
import com.example.progressivelearning_android.viewmodel.LearningGoalViewModel
import kotlinx.android.synthetic.main.fragment_create_learning_goal.*

class CreateLearningGoalFragment : Fragment() {

    private val learningGoalViewModel: LearningGoalViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_learning_goal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setHasOptionsMenu(true)
        initViews()
    }


    private fun initViews() {
        create_btn.setOnClickListener {
            createLearningGoal()
        }
    }

    fun createLearningGoal() {
        val learningGoal = LearningGoal(et_title.text.toString(),
        et_description.text.toString())
        learningGoalViewModel.addLearningGoal(learningGoal)
        navController.navigate(R.id.action_createLearningGoalFragment_to_navigation_dashboard)
    }

}