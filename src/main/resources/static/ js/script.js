$(document).ready(function() {
  $('#userMessageForm').submit(function(event) {
    event.preventDefault(); // Отменяем стандартное поведение формы

    // Отображаем диалоговое окно с анимацией загрузки
    $('#loadingDialog').removeClass('hidden');

    // Делаем задержку в 10 секунд
    setTimeout(function() {
      // Отправляем данные формы на сервер
      $.post($(this).attr('action'), $(this).serialize(), function(response) {
        // При получении ответа от сервера скрываем диалоговое окно с анимацией загрузки
        $('#loadingDialog').addClass('hidden');
        // Отображаем ответ ИИ в контейнере для ответа
        $('#responseContainer').html(response);
      });

    }, 10000); // 10 секунд задержка
  });
});
