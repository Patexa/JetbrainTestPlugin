package com.jetbrains.rider.plugins.sampleplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.ui.SearchTextField
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

/**
 * Action that opens a popup window with a search bar
 */
class SearchPopupAction : AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        showSearchPopup(project)
    }
    
    private fun showSearchPopup(project: Project) {
        val panel = JPanel(BorderLayout())
        panel.preferredSize = Dimension(500, 400)
        
        // Create search field
        val searchField = SearchTextField()
        searchField.textEditor.emptyText.text = "Type to search..."
        
        // Create list to display results
        val listModel = DefaultListModel<String>()
        val resultList = JBList(listModel)
        val scrollPane = JBScrollPane(resultList)
        
        // Sample data - replace with your actual search logic
        val allItems = listOf(
            "Action 1: Open File",
            "Action 2: Navigate to Class",
            "Action 3: Find Usages",
            "Action 4: Refactor",
            "Action 5: Run Tests",
            "Action 6: Debug Application",
            "Action 7: Build Solution",
            "Action 8: Clean Solution",
            "Action 9: Generate Code",
            "Action 10: Format Document"
        )
        
        // Initially show all items
        allItems.forEach { listModel.addElement(it) }
        
        // Add search functionality
        searchField.addDocumentListener(object : javax.swing.event.DocumentListener {
            override fun insertUpdate(e: javax.swing.event.DocumentEvent?) = updateList()
            override fun removeUpdate(e: javax.swing.event.DocumentEvent?) = updateList()
            override fun changedUpdate(e: javax.swing.event.DocumentEvent?) = updateList()
            
            private fun updateList() {
                val searchText = searchField.text.lowercase()
                listModel.clear()
                
                if (searchText.isEmpty()) {
                    allItems.forEach { listModel.addElement(it) }
                } else {
                    allItems
                        .filter { it.lowercase().contains(searchText) }
                        .forEach { listModel.addElement(it) }
                }
            }
        })
        
        // Add components to panel
        panel.add(searchField, BorderLayout.NORTH)
        panel.add(scrollPane, BorderLayout.CENTER)
        
        // Create and show popup
        val popup: JBPopup = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(panel, searchField)
            .setTitle("Search Actions")
            .setFocusable(true)
            .setRequestFocus(true)
            .setMovable(true)
            .setResizable(true)
            .createPopup()
        
        // Handle item selection
        resultList.addMouseListener(object : java.awt.event.MouseAdapter() {
            override fun mouseClicked(e: java.awt.event.MouseEvent?) {
                if (e?.clickCount == 2) {
                    val selectedValue = resultList.selectedValue
                    if (selectedValue != null) {
                        handleSelection(selectedValue, popup)
                    }
                }
            }
        })
        
        // Handle Enter key
        resultList.addKeyListener(object : java.awt.event.KeyAdapter() {
            override fun keyPressed(e: java.awt.event.KeyEvent?) {
                if (e?.keyCode == java.awt.event.KeyEvent.VK_ENTER) {
                    val selectedValue = resultList.selectedValue
                    if (selectedValue != null) {
                        handleSelection(selectedValue, popup)
                    }
                }
            }
        })
        
        popup.showCenteredInCurrentWindow(project)
    }
    
    private fun handleSelection(selectedValue: String, popup: JBPopup) {
        // Handle the selected item - replace with your actual logic
        println("Selected: $selectedValue")
        
        // Show notification
        com.intellij.notification.NotificationGroupManager.getInstance()
            .getNotificationGroup("Sample Plugin Notifications")
            .createNotification("You selected: $selectedValue", com.intellij.notification.NotificationType.INFORMATION)
            .notify(null)
        
        popup.closeOk(null)
    }
}

