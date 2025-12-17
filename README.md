L'architecture du projet est divisée en 3 parties :
- Data : interragis avec l'API
- Domain : Modèles/interfaces des repos
- UI : partie graphique de l'app

On sépare les 3 pour ne dépendre que d'abstractions : si Domain change, on ne change pas l'UI.

Le ViewModel récupère l'interface des repo et appelle des méthodes sans savoir comment elles sont implémentées, grâce à la DI on a ensuite le repo qui est appellé puis l'API.
ViewModel => Repository (interface – Domain) => RepositoryImpl (Data) => API / Local
