
const readline=require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

let storeInventory = [];
let cart = [];

function displayMainMenu() {
    console.log('Welcome to Tezo company 🛒shoppling cart.');
    console.log('choose from the following options:');
  console.log('\nMain Menu');
  console.log('1. Manage Store');
  console.log('2. Sell Items');
  console.log('3. Exit');
  rl.question('\nEnter your option: ', (option) => {
    switch (option) {
      case '1':
        manageStore();
        break;
      case '2':
        sellItemsMenu();
        break;
      case '3':
        rl.close();
        break;
      default:
        console.log('Invalid option. Please try again.');
        displayMainMenu();
    }
  });
}

function manageStore() {
  console.log('\nManage Store Menu');
  console.log('1. Add Product(s)');
  console.log('2. Back');
  console.log('3. Exit');
  rl.question('\nEnter your option: ', (option) => {
    switch (option) {
      case '1':
        addProduct();
        break;
      case '2':
        displayMainMenu();
        break;
      case '3':
        rl.close();
        break;
      default:
        console.log('Invalid option. Please try again.');
        manageStore();
    }
  });
}

function addProduct() {
  rl.question('\nEnter Product details:\nID: ', (id) => {
    rl.question('Name: ', (name) => {
      rl.question('Price: ', (price) => {
        rl.question('Quantity: ', (qty) => {
            let orgQ=qty;
          const product = { id, name, price, qty, orgQ };
          storeInventory.push(product);
          console.log('Product added successfully!');
          rl.question('\nDo you want to add another product? (Y/N): ', (answer) => {
            if (answer.toUpperCase() === 'Y') {
              addProduct();
            } else {
              manageStore();
            }
          });
        });
      });
    });
  });
}


function sellItemsMenu() {
  console.log('\nSell Items Menu');
  console.log('1. Add to Cart');
  console.log('2. Checkout');
  console.log('3. Back');
  console.log('4. Exit');
  console.log('5. Remove ');
  rl.question('\nEnter your option: ', (option) => {
    switch (option) {
      case '1':
        addToCart();
        break;
      case '2':
        checkout();
        break;
      case '3':
        displayMainMenu();
        break;
      case '4':
        rl.close();
        break;
        case '5':
       cart=[];
        break;
      default:
        console.log('Invalid option. Please try again.');
        sellItemsMenu();
    }
  });
}



function addToCart() {
    console.log('\nProduct List:');
    console.log('ID   Product   Price   quantity   orgQ');
    storeInventory.forEach((product) => {


      console.log(`${product.id}    ${product.name}    ${product.price}     ${product.qty}       ${product.orgQ}`);
    });
  
    rl.question('\nSelect item (Enter ID): ', (selectedId) => {
      const selectedProduct = storeInventory.find((product) => product.id === selectedId);
  
      if (selectedProduct) {
        rl.question('Enter quantity to add to cart: ', (quantity) => {
          if (Number(quantity) > 0 && Number(quantity) <= selectedProduct.qty) {
              const cartItem = { ...selectedProduct, quantity: Number(quantity) };
            selectedProduct.qty-=cartItem.quantity;

             
            cart.push(cartItem);
            console.log(`${selectedProduct.name} added to cart!`);
            rl.question('\nDo you want to add another item to cart? (Y/N): ', (answer) => {
              if (answer.toUpperCase() === 'Y') {
                addToCart();
              } else {
                sellItemsMenu();
              }
            });
          } else {
            console.log('Invalid quantity. Please enter a valid quantity.');
            addToCart();
          }
        });
      } else {
        console.log('Invalid product ID. Please try again.');
        addToCart();
      }
    });
  }
  
 
  
  displayMainMenu();
  

function checkout() {
  console.log('\nCheckout');
  console.log('ID   Product    Quantity   Price');
  cart.forEach((item) => {
    console.log(`${item.id}   ${item.name}        ${item.quantity}         ${item.price}`);
  });

  const totalPrice = cart.reduce((total, item) => total + item.quantity * item.price, 0);
  console.log(`\nTotal Price: ${totalPrice}`);

  rl.question('\nDo you want to add another item to cart? (Y/N): ', (answer) => {
    if (answer.toUpperCase() === 'Y') {
      addToCart();
    } else {
    sellItemsMenu();
    }
  });
}

displayMainMenu();


