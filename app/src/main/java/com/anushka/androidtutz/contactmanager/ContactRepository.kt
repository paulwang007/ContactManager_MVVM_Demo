package com.anushka.androidtutz.contactmanager

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.anushka.androidtutz.contactmanager.db.ContactsAppDatabase
import com.anushka.androidtutz.contactmanager.db.entity.Contact
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactRepository(application: Application) {
    val contactsLiveData: LiveData<List<Contact>> = MutableLiveData()
    private val contactsAppDatabase: ContactsAppDatabase =
        Room.databaseBuilder(application, ContactsAppDatabase::class.java, "ContactDB").build()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadAllContacts() {
        compositeDisposable.add(
            contactsAppDatabase.contactDAO.getContacts()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ contacts: List<Contact?>? ->  },
                    { onError: Throwable? -> })

        )
    }

    fun deleteContact(contact: Contact) {
        contactsAppDatabase.contactDAO.deleteContact(contact)
    }

    fun updateContact(contact: Contact) {
        contactsAppDatabase.contactDAO.updateContact(contact)
    }

    fun createContact(name:String, email:String) {
        val temp_id: Long = 0
        contactsAppDatabase.contactDAO.addContact(Contact(temp_id, name, email))
    }

    fun clear() {
        compositeDisposable.clear()
    }
}
