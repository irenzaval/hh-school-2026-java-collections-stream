package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    List<Person> orderedPersons = new ArrayList<>();
    Map<Integer,Person> mapPersons =new HashMap<>();
    for (Person person : persons)
    {
      mapPersons.put(person.id(),person);
    }
    for (Integer id : personIds)
    {
      orderedPersons.add(mapPersons.get(id));
    }
    return orderedPersons;

  }
  //время поиска по значению в карте O(n), по списку тоже O(n), поиск по индексу O(1),получается временная сложность O(2n)=O(n)
}
