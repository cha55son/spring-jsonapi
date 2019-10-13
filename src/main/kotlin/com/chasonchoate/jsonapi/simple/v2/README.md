# V2

This section will focus on the separation of concerns between the objects listed below.
In the previous attempts there was only controllers and resources causing this weird inter-dependence.
Also caused thoughts for re-using controller methods for included resources traversal (which is bad).
So this attempt will be to split the specific use-cases out to see if the access pattern makes sense.

## Ideas

- ResourceController
    - Handles routing. Should be able to change routes easily and clients should still work.
- ResourceSerializer
    - Handles the conversion between a Resource and it's JSON:API equivalent.
- ResourceRepository
    - Manages the retrieval of resources. Most importantly the including of related resources.
      Should allow devs to easily change how resources are queried.
- Resource
    - Manages the access and serialization of the resource.
