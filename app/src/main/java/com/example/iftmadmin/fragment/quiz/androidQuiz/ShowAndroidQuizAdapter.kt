package com.example.iftmadmin.fragment.quiz.androidQuiz

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.iftmadmin.R
import com.example.iftmadmin.fragment.quiz.basicComputerQuiz.BasicComputerQuizModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import kotlin.Int
import kotlin.toString

class ShowAndroidQuizAdapter(
    private val courseList: ArrayList<BasicComputerQuizModel>,
    private val context: Context?=null
): RecyclerView.Adapter<ShowAndroidQuizAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val question : TextView = view.findViewById(R.id.quiz_question)
        val optionA : TextView = view.findViewById(R.id.bacicComOption1)
        val optionB :TextView = view.findViewById(R.id.bacicComOption2)
        val optionC : TextView = view.findViewById(R.id.bacicComOption3)
        val optionD : TextView = view.findViewById(R.id.bacicComOption4)
        val answer : TextView = view.findViewById(R.id.answer)

        val card : MaterialCardView = view.findViewById(R.id.materialCardView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.basic_computer_quiz_cardview,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val basicComputerQuizModel: BasicComputerQuizModel = courseList[position]

            holder.question.setText("Qus.- "+basicComputerQuizModel.question)
        holder.optionA.setText("1."+basicComputerQuizModel.optionA)
        holder.optionB.setText("2."+basicComputerQuizModel.optionB)
        holder.optionC.setText("3."+basicComputerQuizModel.optionC)
        holder.optionD.setText("4."+basicComputerQuizModel.optionD)
        holder.answer.setText("  "+basicComputerQuizModel.answer)





        holder.card.setOnLongClickListener(OnLongClickListener {
            showDialog(basicComputerQuizModel)
            true
        })


    }

    private fun showDialog(basicComputerQuizModel: BasicComputerQuizModel) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("androidQuiz")

        if (context != null) {
            MaterialAlertDialogBuilder(context)
                .setTitle("Delete Quiz")
                .setMessage("Do you want to delete this Quiz ?")
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {


                                Toast.makeText(context, "Quiz deleted", Toast.LENGTH_SHORT).show()

                                databaseRef.child(basicComputerQuizModel.question.toString()).removeValue()
                                courseList.remove(basicComputerQuizModel)


                    }

                }).show()
        }
    }



    override fun getItemCount(): Int {

            return courseList.size
    }
}



