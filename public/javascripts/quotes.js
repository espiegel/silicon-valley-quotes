var quotes = (function () {
    var quotes = {};

    quotes.getRandomQuote = function (callback) {
        $.get("/getRandomQuote",
            function (data, status) {
                callback(data);
            }
        );
    };

    return quotes;
})();

(function () {
    $(document).ready(function () {
        $('#btnQuote').click(function () {
            quotes.getRandomQuote(function (data) {
                var container = $("#quoteContainer");

                container.empty();

                var paragraph = $("<p>", {
                    text: data.quote
                });
                container.append(paragraph);

                var img = $("<img>", {
                    src: "/assets/images/"+data.name.toLowerCase()+".jpg"
                });
                container.append(img);
            })
        })
    })
})
();
