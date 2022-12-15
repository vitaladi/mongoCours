

  // Créez une tâche par défaut pour l'utilisateur

    

    // Ajoutez la nouvelle tâche à la liste des tâches par défaut de l'utilisateur
    users.updateOne(
      Filters.eq("username", username),
      Updates.push("defaultTasks", new Document("name", taskName).append("done", false))
    );
  }






















  // Récupérez les tâches par défaut de l'utilisateur
  public List<Document> getDefaultTasks(String username) {
    // Vérifiez si l'utilisateur existe
    Document user = users.find(Filters.eq("username", username)).first();
    if (user == null) throw new UnknownUserException();

    // Récupérez la liste des tâches par défaut de l'utilisateur
    return user.getList("defaultTasks", Document.class);
  }

  // Supprimez une tâche par défaut de l'utilisateur
  public void deleteDefaultTask(String username, String taskName) {
    // Vérifiez si l'utilisateur existe
    Document user = users.find(Filters.eq("username", username)).first();
    if (user == null) throw new UnknownUserException();

    // Supprimez la tâche de la liste des tâches par défaut de l'utilisateur
    users.updateOne(
      Filters.eq("username", username),
      Updates.pull("defaultTasks", Filters.eq("name", taskName))
    );
  }

  // Marquez une tâche par défaut comme terminée
  public void setDefaultTaskDone(String username, String taskName) {
   
  Document user = users.find(Filters.eq("username", username)).first();
  if (user == null) throw new UnknownUserException();

  // Trouvez l'index de la tâche dans la liste des tâches par défaut de l'utilisateur
  List<Document> defaultTasks = user.getList("defaultTasks", Document.class);
  int taskIndex = -1;
  for (int i = 0; i < defaultTasks.size(); i++) {
    if (defaultTasks.get(i).getString("name").equals(taskName)) {
      taskIndex = i;
      break;
    }
  }
  if (taskIndex == -1) throw new UnknownTaskException();

  // Marquez la tâche comme terminée en utilisant l'opérateur $set
  users.updateOne(
    Filters.eq("username", username),
    Updates.set(String.format("defaultTasks.%d.done", taskIndex), true)
  );
}

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;


public class TodoApp {
  // Créez une instance de MongoClient pour se connecter à la base de données
  MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
  MongoDatabase database = mongoClient.getDatabase("mydb");
  MongoCollection<Document> users = database.getCollection("users");
  
  
  // Vérifiez si l'utilisateur existe
    Document user = users.find(Filters.eq("username", username)).first();
    if (user == null) throw new UnknownUserException();
    //On s'en fou

