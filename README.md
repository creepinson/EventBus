EventBus is an event system which can be used to fire events to registered methods via registering the use of the @EventHandler annotation
<h1>Usage</h1>
To use the event system you'll need an instance of EventManager
<code>
EventManager eventManager = new EventManager();
</code>
To register an event listener all you need to do is call either
<code>
eventManager.addEventListener(object);
</code>
with object being the object which holds your event handler method
or you can use
<code>
eventManager.addSpecificEventListener(object, class);
</code>
this will only register event listeners with a specific event type
Now to fire an event you'll want to use
<code>
eventManager.fireEvent(event)
</code>