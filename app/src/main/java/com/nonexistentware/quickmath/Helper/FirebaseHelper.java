package com.nonexistentware.quickmath.Helper;

import com.google.firebase.database.*;

import java.util.*;

public class FirebaseHelper {
    private DatabaseReference databaseRef;
    private List<Long> values;
    private ValueEventListener valueEventListener;


    public interface OnSortCompleteListener {
        void onSortComplete(List<Long> sortedValues);

        void onError(DatabaseError databaseError);
    }

    public FirebaseHelper(DatabaseReference databaseRef) {
        this.databaseRef = databaseRef;
        this.values = new ArrayList<>();
    }

    public void sortValues(final OnSortCompleteListener listener) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Players");
        valueEventListener = databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                values.clear();

                // Iterate over the data and extract values
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object valueObject = childSnapshot.getValue();
                    if (valueObject instanceof Long) {
                        values.add((Long) valueObject);
                    } else if (valueObject instanceof HashMap) {
                        // Handle HashMap values if necessary
                        // In this example, we'll convert the HashMap to a long if possible
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) valueObject;
                        Object longValue = hashMap.get("value");
                        if (longValue instanceof Long) {
                            values.add((Long) longValue);
                        }
                    }
                }

                // Sort the values
                Collections.sort(values);

                // Notify the listener with the sorted values
                listener.onSortComplete(values);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError);
            }
        });
    }

    public void cleanup() {
        if (databaseRef != null && valueEventListener != null) {
            databaseRef.removeEventListener(valueEventListener);
        }
    }
}
