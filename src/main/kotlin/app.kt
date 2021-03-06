import javafx.application.Application
import javafx.stage.Stage

internal fun main() = Application.launch(FunctionalKotlinSample::class.java)

class FunctionalKotlinSample : Application() {

    lateinit var binder: Binder<RootState>

    val dataSource = DataSourceImpl()
    val store = StoreImpl().apply { observe { binder.bind(it) } }
    val middlewares = listOf(MiddleWareImpl(dataSource::downloadNames, store::push))

    override fun start(primaryStage: Stage) {
        val rootView = RootView(primaryStage)
        binder = BinderImpl(rootView, ::dispatchAction)
        dispatchAction(Refresh)
    }

    fun dispatchAction(action: Action) {
        return store.state.let { state ->
            store.push(reduce(state, action))
            middlewares.forEach { it.handleAction(state, action) }
        }
    }
}
