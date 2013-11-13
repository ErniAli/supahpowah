(define proc
  (lambda (a b)
    (let ((sum (+ a b)))
      sum)
))

(define x 2)
(define y 3)
(proc x y)

(define func
  (lambda (a)
    (let* ((b 2)
           (prod (* a b)))
      prod)))
