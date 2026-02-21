package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    /*if (persons.size() == 0) {
      return Collections.emptyList();
    }
    persons.remove(0);
    return persons.stream().map(Person::firstName).collect(Collectors.toList());*/
    if(persons==null|| persons.isEmpty())
      return List.of();
    return persons.stream()
    .map(Person::firstName)
    .skip(1)
    .collect(Collectors.toList());
    //лучше проверять на null(существование объекта) и пустоту одновременно,также вместо того, чтобы изменять исходный список, лучше пропускать один элемент, ничего не меняя
  }


  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    //return getNames(persons).stream().collect(Collectors.toSet());
    return new HashSet<>(getNames(persons));   /*2 варианта исправления, если делать через стрим(тем более как мы выучили с лекций, он тут излишний),
    то метод distinct явно не нужен, мы и так собираем имена во множество, которое гарантирует уникальность*/
    //второй вариант - просто образовать новое множество из полученного списка имен, в него попадут только различные имена
    }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(),person.middleName(),person.secondName())
    .filter(obj -> obj!=null)
    .filter(s -> !s.isBlank())
    .collect(Collectors.joining(" "));
    /*через стрим создаем поток из массива со строками, фильтруем их на null и пустые(или только с пробелами) строки и собираем в одну через пробел
     делаем код короче и более читаемым, умещая все в 4 строчки, плюс все преобразования происходят параллельно*/
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer,String> mapPersons =new HashMap<>();
    for (Person person : persons)
    {
      mapPersons.put(person.id(),convertPersonToString(person));
    }
    return mapPersons;
    //давать начальную емкость словарю нет смысла, он все равно ее увеличивает на автомате, почему бы просто его не сделать пустым вначале?
    //проверять вручную дубликаты ключей тоже лишнее, HashMap сам их фильтрует(точнее перезаписывает значение)
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    return persons1.stream()
    .anyMatch(persons2::contains);
    //благодаря волшебному методу, позволяющему проверить соотвествие условию хотя бы 1 элемента, проверяем содержит ли вторая коллекция какие-то элементы первой
    //код намного более читаемый, лучше по производительности(останавливается после первого совпадения, первый же проходит итерацию полностью)
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(n -> n%2==0).count();
    //избавляемся от создания переменной
    //forEach излишний для подсчеты количества, поэтому стоит заменить на метод count(), занимающийся как раз подсчетом элементов
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
  //могу предположить, что дело в хэш-кодах, известно, что для типов Integer хэш-код равен самому значению типа, плюс HashSet распределяет элементы по корзинам
  // на основе хэш-кодов, и обход корзин происходит по порядку создания, в итоге хэш-коды получаются равными
}
