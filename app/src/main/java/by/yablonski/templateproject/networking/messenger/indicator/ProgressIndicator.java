package by.yablonski.templateproject.networking.messenger.indicator;

/**
 * Способен управлять отображением загрузки
 *
 * @author Andrey Yablonsky
 */
public interface ProgressIndicator {

    /**
     * <p>
     * Запускает отображение индикатора загрузки.
     * </p>
     * Если progress уже запущен, то просто увеличивается счетчик запросов.
     * Прогресс будет отображаться, пока счетчик не станет равным нулю.
     */
    void show();

    /**
     * <p>
     * Запрос на прекращение отображение индикатора загрузки.
     * </p>
     * Уменьшает счетчик запросов и прекращает отображение progress, если число счетчик запросов равен нулю.
     */
    void hide();

    /**
     * Определяет, активен ли в данный момент индикатор загрузки.
     *
     * @return boolean true, если интикатор загрузки активен.
     */
    boolean isVisible();

}
