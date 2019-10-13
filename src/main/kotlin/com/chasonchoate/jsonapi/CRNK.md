# CRNK comparison

## Reasons CRNK isn't great

- Repository pattern diverges from typical Spring MVC.
- Repository methods don't map exactly to JSON:API actions.
    - For example, `findOne` and `findAll` do to much stuff. (Referring to all the filters, nested, etc)
