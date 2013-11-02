; Convert an infix polynomial into a list of sublists,
; where each sublist is a term.
(define terminize
  (lambda (poly)
    (cond
      ((null? poly) '())
      (else (cons (upto '+ poly) (terminize (after '+ poly))))
    )
  )
)
