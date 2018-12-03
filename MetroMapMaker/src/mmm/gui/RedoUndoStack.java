/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmm.gui;

import java.util.Stack;

/**
 * This class save the information about the data for redo and undo.
 * @author Yonghun Jeong
 * @version 1.0
 */
public class RedoUndoStack<E> extends Stack<E> {
    private Stack undoStack;
    private Stack redoStack;

    /**
     * Constructor for initializing the RedoUndoStack, note that this constructor
     * will fully setup the RedoUndoStack for use.
     */
    public RedoUndoStack() {
        undoStack = new Stack();
        redoStack = new Stack();
    }
    
    /**
     * This function pushes and returns the result on the top of the stack 
     * 
     * @return value :the top value of the stack 
     * @param value 
     */
    public E push(E value) {
        super.push(value);
        undoStack.push("push");
        redoStack.clear();
        return value;
    }

    /**
     * This function pops and returns the result on the top of the stack 
     * 
     * @return value :the top value of the stack 
     */
    public E pop() {
        E value = super.pop();
        undoStack.push(value);
        undoStack.push("pop");
        redoStack.clear();
        return value;
    }

    /**
     * This function returns the true or false whether or not an undo can be done.
     * 
     * @return !undoStack.isEmpty() :whethere the undo can be done or not
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * This function undo the last stack push or pop command
     * 
     * @throws IllegalStateException : Thrown if the undoStack is Empty()
     */
    public void undo() {
            if (!canUndo()) {
            throw new IllegalStateException();
        }
        Object action = undoStack.pop();
        if (action.equals("push")) {
            E value = super.pop();
            redoStack.push(value);
            redoStack.push("push");
        } else {
            E value = (E) undoStack.pop();         
            super.push(value);
            redoStack.push("pop");
        }
    }

    /**
     * This function returns the true or false whether or not an redo can be done.
     * 
     * @return !redoStack.isEmpty() :whethere the redo can be done or not
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    /**
     * This function redo the last stack undo operation
     * 
     * @throws IllegalStateException : Thrown if the redoStack is Empty()
     */
    public void redo() {
        if (!canRedo()) {
            throw new IllegalStateException();
        }
        Object action = redoStack.pop();
        if (action.equals("push")) {
            E value = (E) redoStack.pop();
            super.push(value);
            undoStack.push("push");
        } else {
            E value = super.pop();
            undoStack.push(value);
            undoStack.push("pop");
        }
    }
}