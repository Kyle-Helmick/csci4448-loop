# CSCI4448 Project Writeup

## Name: Kyle Helmick

## GitHub Link: github.com/Kyle-Helmick/csci4448-loop

## Title: Loop

## Project Summary

Loop is a fake social media platform for developers. Users sign up with Github and effectively have access to a twitter clone. They can edit their handle, location, and name and make posts to their "page". If the user chooses to, they can create a group that others can join and make posts into the group (everything is public just like twitter).

## Project Requirements

| id | requirements |
|--- |---           |
| 00 | The server authenticates with Github OAuth2
| 01 | The server can access OAuth2 user data returned from Github
| 02 | The server initializes the users' profile using the data from Github in the form of a User object
| 03 | The server creates a document in MongoDB for the user profile under the collection users
| 04 | The server creates a websocket integrated session for the user that times out at 1 month (prompting the user to sign back in)
| 05 | The user can edit their bio, handle, location, and profile picture
| 06 | The user can make a post
| 07 | The user can edit the content of a post
| 08 | The user can delete a post
| 08 | The user can make a group
| 09 | The user can edit the bio and handle of any group that they own
| 10 | The user can delete a group
| 11 | The server will delete any posts or reposts from a group after deletion of said group
| 12 | The group owner can delete any post made in their group
| 13 | The user can logout

## UI Mockups

See /ui_mockups

## Class Diagram

See /class_diagrams

*Floating classes / classes that confict with the writeup requirements are configuration classes (and won't have connections unless I include the UML diagrams of my dependencies).*

*what I wrote (not to configure my project) has connections in the class diagram.*