# Chatty User Guide

// Update the title above to match the actual product name

(/assets/images/electrocat.png)

Chatty helps you manage your everyday tasks, from to-dos to events and deadlines

## Features

### Feature: Task Listing
Displays all tasks currently stored in the system.

#### Usage
`list`

#### Expected Output
```
1. [T] Read book
2. [D] Submit report (by: Mar 1 2025)
```

### Feature: Marking Tasks
Marks a task as completed.

#### Usage
`mark <task number>`

#### Expected Output
```
OK, I've marked this task as done: <task details>
```

### Feature: Unmarking Tasks
Unmarks a completed task.

#### Usage
`unmark <task number>`

#### Expected Output
```
OK, I've marked this task as not completed: <task details>
```

### Feature: Deleting Tasks
Deletes a task from the list.

#### Usage
`delete <task number>`

#### Expected Output
```
Noted. I've removed this task: <task details>
Now you have <number of tasks> items in the list
```

### Feature: Finding Tasks
Finds tasks that contain a given keyword.

#### Usage
`find <keyword>`

#### Expected Output
```
Here are the matching tasks in your list:
1. <task details>
2. <task details>
```

```
No tasks matching your search were found.
```

### Feature: Recurring Tasks
Sets a recurring task for a given period.

#### Usage
`recur <task number> /by <frequency> /for <count>`

#### Example
```
recur 1 /by weekly /for 4
```

#### Expected Output
```
Successfully recurred <task details> <frequency> for <no. of occurrences> occurrences.",
```

### Feature: Today's Tasks
Displays tasks scheduled for today.

#### Usage
`today`

#### Expected Output
```
Here are your tasks for today:
1. <task details>
2. <task details>
```

```
No tasks for today!
```

### Feature: Adding To-Do Tasks
Adds a general to-do task.

#### Usage
`todo <task name>`

#### Example
```
todo Buy groceries
```

#### Expected Output
```
Task added: [T] <task name>
Now you have <number of tasks> items in the list.
```

### Feature: Adding Events
Adds an event task with a specific date and time.

#### Usage
`event <event name> /from <datetime> /to <datetime>`

#### Example
```
event Team meeting /at 2025-03-02 10:00 AM
```

#### Expected Output
```
Task added: [E] <title> (from: <datetime> to: <datetime>)
Now you have <number of tasks> items in the list.
```

### Feature: Adding Deadlines
Adds a deadline task.

#### Usage
`deadline <task name> /by <due date>`

#### Example
```
deadline Submit report /by 2025-03-01
```

#### Expected Output
```
Task added: [D] <title> (by: <datetime>)
Now you have <number of tasks> items in the list.
```


