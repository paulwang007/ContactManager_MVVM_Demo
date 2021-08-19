package com.anushka.androidtutz.contactmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.anushka.androidtutz.contactmanager.db.entity.Contact

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val contactRepository: ContactRepository = ContactRepository(application)

    fun getAllContacts(): LiveData<List<Contact>> {
        return contactRepository.contactsLiveData
    }

    fun createContact(name: String, email: String) {
        contactRepository.createContact(name, email)
    }

    fun deleteContact(contact: Contact) {
        contactRepository.deleteContact(contact)
    }

    fun updateContact(contact: Contact) {
        contactRepository.updateContact(contact)
    }

    fun clearDisposable() {
        contactRepository.clear()
    }
}
