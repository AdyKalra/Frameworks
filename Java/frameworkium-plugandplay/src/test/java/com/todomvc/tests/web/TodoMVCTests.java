package com.todomvc.tests.web;

import com.frameworkium.core.ui.tests.BaseTest;
import com.todomvc.pages.web.BasicTodoMvcPage;
import org.junit.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.Stories;

//import org.testng.SkipException;

@Features("TodoMVC")
public class TodoMVCTests extends BaseTest {

    public String itemName = "";

    @Issue("TODOMVC-1")
    @Stories("Add a Todo Item")
    @Test(description = "Add an item")
    public void testAddTodoItem() {

        boolean iResult =  BasicTodoMvcPage.open().then()
                .addTodoItem("FirstItem");

        Assert.assertTrue("1. I want to add a To-do item", iResult);

    }

    @Issue("TODOMVC-2")
    @Stories("Edit a Todo Item")
    @Test(description = "Edit an item")
    public void testEditTodoItem() {
        itemName = "Item to be edited";
        BasicTodoMvcPage.open().then().addTodoItem(itemName);
        Assert.assertTrue("2. I want to edit the content of an existing To-do item", BasicTodoMvcPage.open().editTodoItem(itemName, "Item updated"));

    }

    @Issue("TODOMVC-3")
    @Stories("testCompleteTodoItem")
    @Test(description = "testCompleteTodoItem")
    public void testCompleteTodoItem(){
        itemName = "Item created for completion";
        BasicTodoMvcPage.open().then().addTodoItem(itemName);
        Assert.assertTrue("3. I can complete a To-do by clicking inside the circle UI to the left of the To-do", BasicTodoMvcPage.open().completeTodoItem(itemName));
        BasicTodoMvcPage.open().clearAllCompletedTodoItems();
    }

    @Issue("TODOMVC-4")
    @Stories("testReactivateTodoItem")
    @Test(description = "testReactivateTodoItem")
    public void testReactivateTodoItem(){
        itemName = "Item created for completion";
        BasicTodoMvcPage.open().then().addTodoItem(itemName);
        BasicTodoMvcPage.open().completeTodoItem("Item created for completion");
        Assert.assertTrue("4. I can re-activate a completed To-do by clicking inside the circle UI", BasicTodoMvcPage.open().reActivateItem(itemName));

    }

    @Issue("TODOMVC-5")
    @Stories("testAddSecondTodoItem")
    @Test(description = "testAddSecondTodoItem")
    public void testAddSecondTodoItem(){
        Assert.assertTrue(BasicTodoMvcPage.open().then().addTodoItem("FirstItem"));
        Assert.assertTrue("5. I can add a second To-do", BasicTodoMvcPage.open().addTodoItem("SecondItem"));
    }

    @Issue("TODOMVC-6")
    @Stories("testCompleteAllTodoItems")
    @Test(description = "testCompleteAllTodoItems")
    public void testCompleteAllTodoItems(){
        BasicTodoMvcPage.open().then().addTodoItem("Item created to test Complete All Items functionality");
        Assert.assertTrue("6. I can complete all active To-dos by clicking the down arrow at the top-left of the UI", BasicTodoMvcPage.open().completeAllItems());
        BasicTodoMvcPage.open().clearAllCompletedTodoItems();

    }

    @Issue("TODOMVC-7")
    @Stories("testFilterCompletedItems")
    @Test(description = "testFilterCompletedItems")
    public void testFilterCompletedItems(){
        itemName = "Item created to test Filter by completion status functionality";
        BasicTodoMvcPage.open().then().addTodoItem(itemName);
        Assert.assertTrue("7. I can filter the visible To-dos by Completed state", BasicTodoMvcPage.open().searchItemByCompletedStatus(itemName));
        BasicTodoMvcPage.open().clearAllCompletedTodoItems();

    }

    @Issue("TODOMVC-8")
    @Stories("testDeleteTodoItem")
    @Test(description = "testDeleteTodoItem")
    public void testDeleteTodoItem(){
        itemName = "Item created for deletion";
        BasicTodoMvcPage.open().then().addTodoItem(itemName);
        Assert.assertTrue("8. I can clear a single To-do item from the list completely by clicking the Close icon", BasicTodoMvcPage.open().deleteTodoItem(itemName));

    }

    @Issue("TODOMVC-9")
    @Stories("testClearAllTodoItems")
    @Test(description = "testClearAllTodoItems")
    public void testClearAllTodoItems(){
        BasicTodoMvcPage.open().then().addTodoItem("Item created to test Clear all items functionality");
        BasicTodoMvcPage.open().completeAllItems();
        Assert.assertTrue("9. I can clear all completed To-do items from the list completely", BasicTodoMvcPage.open().clearAllCompletedTodoItems());

    }


}
