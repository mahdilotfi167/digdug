 - using a queue for handle keys
> ```java
> public class agent {
>   Queue keys = new Queue()
> ...
> }
> scene.setOnKeyPressed(event -> (agent.addKey(event.getCode)))
> ```
> in this way we can simply handle deactivating game

 - create update method and update every gameObject on it
 - create rigidbody component to handle physics and gravity for stones or others
 - using javafx property listeners that uses observer pattern
 - using binary operators for saving extra data in one number
> 1 2 4 8 16 ...
> 19 = 1 + 2 + 16
> 19 & 1 = 1
> 19 & 2 = 2
> 19 & 4 = 0
> 19 & 8 = 0
> 19 & 16 = 16