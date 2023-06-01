(define (print9orlower I)
    (if (integer? I)
        (if (< I 10)   
            I   
            (print9orlower(- I 1))))
)