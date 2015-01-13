EventBus is an event system which can be used to fire events to registered methods via registering the use of the @EventHandler annotation
<br /><h1>Usage</h1><br />
To use the event system you'll need an instance of EventManager<br />
<code>
EventManager eventManager = new EventManager();
</code><br />
To register an event listener all you need to do is call either<br />
<code>
eventManager.addEventListener(object);
</code><br />
with object being the object which holds your event handler method
or you can use<br />
<code>
eventManager.addSpecificEventListener(object, class);
</code><br />
this will only register event listeners with a specific event type
Now to fire an event you'll want to use<br />
<code>
eventManager.fireEvent(event)
</code>