package com.example.weijinghusandoridlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weijinghusandoridlabs.databinding.ActivityChatRoomBinding;
import com.example.weijinghusandoridlabs.databinding.SentMessageBinding;
import com.example.weijinghusandoridlabs.databinding.ReceiveMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;
    private ChatRoomViewModel chatModel;

    ChatMessageDAO mDAO;

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{

                int position= getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

                AlertDialog.Builder no = builder.setNegativeButton("No", (dialog, cl) -> {
                });
                builder.setMessage("Do you want to delete the message:" + messageText.getText());
                 builder.setTitle("Question:");
                builder.setNegativeButton("No", (dialog, cl) ->{} );
                builder.setPositiveButton("Yes",((dialog, cl) ->{
                            ChatMessage m  = messages.get(position);
                            mDAO.deleteMessage(m);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            
                            Snackbar.make(messageText, "deleted message #"+position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", (cl2) ->{
                                        messages.add(position, m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                        } ))
                        .create().show();
            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").allowMainThreadQueries().build();
        //create dao
        mDAO = db.cmDAO();
        
        //create chat view model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        //get all messages from the database at the beginning
        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(new Runnable() {
                @Override
                public void run() {
                    messages.addAll(mDAO.getAllMessages());
                    runOnUiThread(()->binding.recycleView.setAdapter(myAdapter));
                }
            });
            // chatModel.messages.postValue( messages = new ArrayList<>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.sendButton.setOnClickListener(click ->{
//            messages.add(binding.textInput.getText().toString());
//            myAdapter.notifyItemChanged(messages.size()-1);
//            //clear the previous text
//            binding.textInput.setText("");
//        });

        // Set click listener for Send button
        binding.sendButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());

                //Create sent message object
                //ChatMessage sentMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, true);
                 ChatMessage obj = new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,true);
                mDAO.insertMessage(obj);
                 messages.add(obj);

                //messages.add(sentMessage);

                //notify insert
                myAdapter.notifyItemInserted(messages.size() - 1);
                // Clear the textInput field
                binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click ->{
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());

                //Create chat object
                ChatMessage obj = new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,false);
                mDAO.insertMessage(obj);
                messages.add(obj);
                
                //Create sent message object
                //ChatMessage receiveMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, false);
                //messages.add(receiveMessage);
                //notify insert
                myAdapter.notifyItemInserted(messages.size() - 1);
                // Clear the textInput field
                binding.textInput.setText("");
        });

        //set up recycler view adapter
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    SentMessageBinding sB = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sB.getRoot());
                }
                else {
                    ReceiveMessageBinding rB = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(rB.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                holder.messageText.setText("");
//                holder.timeText.setText("");
//                // Retrieve the object for the specific position in the messages list
//                String obj = messages.get(position);
//
//                // Set the messageText of the holder with the retrieved object
//                holder.messageText.setText(obj);

                holder.messageText.setText("");
                holder.timeText.setText("");

                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount(){
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                ChatMessage obj = messages.get(position);
                if(obj.isSentButton() == true){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

}