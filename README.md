# Event Bus
EventBus is an event system which can be used to fire events to registered methods via registering the use of the @EventHandler annotation.
Most of the credit goes to the original author [X3rces](https://github.com/X3rces/EventBus).

## Usage
To use the event system you'll need an instance of EventManager<br />
```java
EventManager eventManager = new EventManager();
```

To register an event listener all you need to do is call either<br />
```java
eventManager.addEventListener(object);
```

with object being the object which holds your event handler method
or you can use  
```java
eventManager.addSpecificEventListener(object, class);
```
This will only register event listeners with a specific event type
Now to fire an event you'll want to use  
```java
eventManager.fireEvent(event)
```