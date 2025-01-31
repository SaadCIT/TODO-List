package com.example.todolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.todolist.databinding.FragmentAddBinding
import java.util.Calendar


class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding
    var showTime:String? = null
    var showType: String? = null
    lateinit var database: NoteDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater,container,false)

        database = Room.databaseBuilder(requireActivity(),NoteDatabase::class.java,"Ntoe_DB")
            .allowMainThreadQueries().build()


        binding.dateBtn.setOnClickListener {
            pickdate()
        }

        binding.timeBtn.setOnClickListener {
            picktime()
        }
        binding.submitBtn.setOnClickListener {

            val titelstr = binding.titleET.text.toString()
            val datestr = showType ?: "00/00/0000"
            val timestr = showTime ?: "00:00"
            val note = Note(titel = titelstr, time = timestr, date = datestr)
            database.getNoteDao().insertData(note)


            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        }

        return binding.root
    }
    private fun picktime() {
        val calendar = Calendar.getInstance()

        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val timePicker =   TimePickerDialog(requireActivity(),TimePickerDialog.OnTimeSetListener{view,hourOfDay, minute->

            showTime = "$hour:$minute"


            binding.timeBtn.text = showTime
        }, hour,minute,false
        )
        timePicker.show()
    }

    private fun pickdate() {
        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val showdata = DatePickerDialog(
            requireActivity(), DatePickerDialog.OnDateSetListener{ view, dayOfMonth, month, year ->


                showType = "$day/${month + 1}/$year"
                binding.dateBtn.text = showType
            },year, month, day
        )
        showdata.show()
    }


}