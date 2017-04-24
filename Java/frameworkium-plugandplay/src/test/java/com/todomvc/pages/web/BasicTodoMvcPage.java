package com.todomvc.pages.web;

import com.frameworkium.core.ui.annotations.Visible;
import com.frameworkium.core.ui.pages.BasePage;
import com.frameworkium.core.ui.pages.PageFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import org.openqa.selenium.interactions.Actions;

public class BasicTodoMvcPage extends BasePage<BasicTodoMvcPage> {

    @Visible
    @Name("New TodoItem")
    @FindBy(css = "#new-todo")
    private WebElement newTodo;

    @Name("Edit TodoItem")
    @FindBy(xpath = ".//*[@id='ember267']/input")
    private WebElement editTodo;

    public static Actions actions = null;



    @Step("Navigate to http://todomvc.com")
    public static BasicTodoMvcPage open() {
        return PageFactory.newInstance(
                BasicTodoMvcPage.class, "http://todomvc.com/examples/emberjs/");
    }


    @Step("Adding item in search todo box")
    public boolean addTodoItem(String itemName){
        newTodo.click();
        newTodo.isDisplayed();
        newTodo.clear();
        newTodo.sendKeys(itemName);
        newTodo.sendKeys(Keys.ENTER);
        return driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed();
    }

    @Step("Edit item in search todo box")
    public boolean editTodoItem(String itemName, String updatedText){

        WebElement label = driver.findElement(By.xpath("//label[.='" + itemName + "']"));
        Assert.assertTrue(label.isDisplayed());
        actions = new Actions(driver);
        actions.doubleClick(label).build().perform();
        editTodo.clear();
        editTodo.sendKeys(updatedText);
        editTodo.sendKeys(Keys.ENTER);
        return driver.findElement(By.xpath("//label[.='" + updatedText + "']")).isDisplayed();
    }


    @Step("Complete the todo item")
    public  boolean completeTodoItem(String itemName){
        Assert.assertTrue(driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed());
        WebElement divElt = driver.findElement(By.xpath("//label[.='" + itemName + "']")).findElement(By.xpath(".."));
        divElt.findElement(By.tagName("input")).click();
        return driver.findElement(By.cssSelector("#clear-completed")).isDisplayed();
    }

    @Step("Rreactivate the item")
    public boolean reActivateItem(String itemName){
        WebElement todoItem = driver.findElement(By.xpath("//label[.='" + itemName + "']"));
        WebElement divClass = todoItem.findElement(By.xpath(".."));
        WebElement chkComplete = divClass.findElement(By.tagName("input"));
        chkComplete.click();
        WebElement liClass = null;
        try{
            liClass = divClass.findElement(By.xpath(".//*[@id='clear-completed']"));
            if (liClass.isDisplayed()){
                return false;
            }
        }catch (Exception e){
        }
        return true;
    }

    @Step("complete all items added")
    public boolean completeAllItems(){
        String todoItemsCount = driver.findElement(By.cssSelector("#todo-count")).getText();
        if (!todoItemsCount.contains("0")){
            driver.findElement(By.cssSelector("#toggle-all")).click();
            todoItemsCount = driver.findElement(By.cssSelector("#todo-count")).getText();
            if (todoItemsCount.contains("0"))return true;
        }
        return false;
    }

    @Step("delete todo item")
    public boolean deleteTodoItem(String itemName){
        Assert.assertTrue(driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed());
        WebElement divElt = driver.findElement(By.xpath("//label[.='" + itemName + "']")).findElement(By.xpath(".."));
        driver.findElement(By.xpath("//label[.='" + itemName + "']")).click();
        divElt.findElement(By.tagName("button")).click();
        try{
            driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed();
        }catch(Exception e){
            return true;
        }
        return false;
    }

    @Step("Searching item added by comleted status")
    public boolean searchItemByCompletedStatus(String itemName){
        boolean iResult = false;
        if (completeTodoItem(itemName)){
            driver.findElement(By.xpath("//a[@href='#/completed']")).click();
            iResult = driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed();
            driver.findElement(By.xpath("//a[@href='#/']")).click();
        }
        return iResult;
    }

    @Step("reActivateTodoItem")
    public boolean reActivateTodoItem(String itemName){
        Assert.assertTrue(driver.findElement(By.xpath("//label[.='" + itemName + "']")).isDisplayed());
        WebElement divElt = driver.findElement(By.xpath("//label[.='" + itemName + "']")).findElement(By.xpath(".."));
        divElt.findElement(By.tagName("input")).click();
        WebElement liElt = divElt.findElement(By.xpath(".."));
        return (liElt.getAttribute("class").equals("ember-view"));
    }

    @Step("Clear all the completed items from todo")
    public boolean clearAllCompletedTodoItems(){
        Assert.assertTrue(driver.findElement(By.cssSelector("#clear-completed")).isDisplayed());
        driver.findElement(By.cssSelector("#clear-completed")).click();
        try{
            if (!driver.findElement(By.cssSelector("#clear-completed")).isDisplayed()) return true;
        }catch (Exception e){
            return true;
        }
        return false;
    }

}
