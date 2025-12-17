L'architecture du projet est divisée en 3 parties :
- Data : interragis avec l'API
- Domain : Modèles/interfaces des repos
- UI : partie graphique de l'app

On sépare les 3 pour ne dépendre que d'abstractions : si Domain change, on ne change pas l'UI.