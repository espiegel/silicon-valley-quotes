var quotes = (function () {
    var quotes = {};

    quotes.getRandomQuote = function (callback) {
        $.get("/getRandomQuote",
            function (data, status) {
                callback(data);
            }
        );
    };

    quotes.updateQuoteWithData = function(data) {
        var container = $(".quoteContainer");

        container.empty();

        var paragraph = $("<p>", {
            text: data.quote,
            "class": "quoteText"
        });
        container.append(paragraph);

        var imgContainer = $("<div>", {
            "class": "quoteImageContainer"
        });

        var imgTitle = $("<p>", {
            text: data.name,
           "class": "quoteImageTitle"
        });

        var img = $("<img>", {
            src: "/assets/images/"+data.name.toLowerCase()+".jpg",
            title: data.name,
            "class": "quoteImage"
        });
        img.attr('alt', data.name);

        imgContainer.append(imgTitle);
        imgContainer.append(img);
        container.append(imgContainer);
    };

    quotes.generateQuote = function() {
        quotes.getRandomQuote(function(data) {
            quotes.updateQuoteWithData(data);
        });
    };

    return quotes;
})();

(function () {
    $(document).ready(function () {

        quotes.generateQuote();

        $('.btnQuote').click(function () {
            quotes.generateQuote();
        })
    })
})
();
