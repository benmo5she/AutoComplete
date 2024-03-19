package com.bottomline.auto.complete.model;

/*
    Represents a node in a data structure where each initialized node
    represents a character for a word that is intended to be stored in the data structure.
 */
public class Node {

    //As required, we will only work with alpha characters, which will be used to support characters to store.
    public static final int VALID_CHARACTERS_AMOUNT = 26;
    private Node[] nextChars;
    private char data;
    private boolean isWordComplete;


    public Node()
    {
        setNextChars(new Node[VALID_CHARACTERS_AMOUNT]);
    }

    public Node(char data)
    {
        setNextChars(new Node[VALID_CHARACTERS_AMOUNT]);
        this.setData(data);
    }

	public Node[] getNextChars() {
		return nextChars;
	}

	public void setNextChars(Node[] nextChars) {
		this.nextChars = nextChars;
	}

	public char getData() {
		return data;
	}

	public void setData(char data) {
		this.data = data;
	}

	public boolean isWordComplete() {
		return isWordComplete;
	}

	public void setWordComplete(boolean isWordComplete) {
		this.isWordComplete = isWordComplete;
	}

}