object SCALA_1_13_2023_2 extends App:
    //Lenses
    case class Lens[O, V](
        get: O => V,
        set: (O, V) => O
    )

    object Lens:
        def compose[Outer, Inner, Value](
            outer: Lens[Outer, Inner],
            inner: Lens[Inner, Value]
        ) = Lens[Outer, Value](
            get = outer.get andThen inner.get,
            set = (obj, value) => outer.set(obj, inner.set(outer.get(obj), value))
        )
    
    case class Address(no: String, street: String, city: String, state: String, zip: String)
    case class Customer(id: Int, name: String, address: Address)

    trait AddressLenses {
        protected val noLens = Lens[Address, String](
            get = _.no,
            set = (o, v) => o.copy(no = v)
        )

        protected val streetLens = Lens[Address, String](
            get = _.street,
            set = (o, v) => o.copy(street = v)
        )

        protected val cityLens = Lens[Address, String](
            get = _.city,
            set = (o, v) => o.copy(city = v)
        )

        protected val stateLens = Lens[Address, String](
            get = _.state,
            set = (o, v) => o.copy(state = v)
        )

        protected val zipLens = Lens[Address, String](
            get = _.zip,
            set = (o, v) => o.copy(zip = v)
        )
    }

    trait CustomerLenses {
        protected val nameLens = Lens[Customer, Int](
            get = _.id,
            set = (o, v) => o.copy(id = v)
        )

        protected val addressLens = Lens[Customer, Address](
            get = _.address,
            set = (o, v) => o.copy(address = v)
        )
    }

    object LensApp extends AddressLenses with CustomerLenses:
        import Lens._

        val a = Address(no = "B-12", street = "Monroe Street", city = "Denver", state = "CO", zip = "80231")
        val c = Customer(12, "John D Cook", a)

        val custAddrNoLens = compose(addressLens, noLens)
        custAddrNoLens.get(c)
        custAddrNoLens.set(c, "B675")
        

    
    
