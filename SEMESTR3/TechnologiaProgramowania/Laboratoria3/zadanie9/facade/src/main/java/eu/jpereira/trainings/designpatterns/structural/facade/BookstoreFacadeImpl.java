package eu.jpereira.trainings.designpatterns.structural.facade;
import eu.jpereira.trainings.designpatterns.structural.facade.service.BookDBService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.CustomerDBService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.CustomerNotificationService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.OrderingService;
import eu.jpereira.trainings.designpatterns.structural.facade.service.WharehouseService;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Customer;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Book;
import eu.jpereira.trainings.designpatterns.structural.facade.model.DispatchReceipt;
import eu.jpereira.trainings.designpatterns.structural.facade.model.Order;



public class BookstoreFacadeImpl implements BookstoreFacade {
	public CustomerDBService customerService;
	public BookDBService bookService;
	public OrderingService orderingService;
	public CustomerNotificationService customerNotificationService;
	public WharehouseService warehouseService;
  public BookstoreFacadeImpl() {
  }
  public void setCustomerDBService(CustomerDBService customerService) {
    this.customerService = customerService;
  }
  public void setBookDBService(BookDBService bookService) {
    this.bookService = bookService;
  }
  public void setOrderingService(OrderingService orderingService) {
    this.orderingService = orderingService;
  }
  public void setCustomerNotificationService(CustomerNotificationService customerNotificationService) {
    this.customerNotificationService = customerNotificationService;
  }
  public void setWharehouseService(WharehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }
  @Override
  public void placeOrder(String customerId, String isbn) {
      Book book = bookService.findBookByISBN(isbn);
      Customer customer = customerService.findCustomerById(customerId);
      Order order = orderingService.createOrder(customer, book);
      DispatchReceipt receipt = warehouseService.dispatch(order);
      customerNotificationService.notifyClient(receipt);
  }
}
