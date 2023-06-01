        program fizzbuzz
        implicit none

        integer      i
        character*32 text


        i = 0
            do while (i < 100)
                i = i + 1
                text = ''

               
                if (MOD(i,15) == 0) then
                    text = 'FizzBuzz'
                elseif (MOD(i,5) == 0) then
                    text = 'Buzz'
                elseif (MOD(i,3) == 0) then
                    text = 'Fizz'
                else
                    write (text,*) i
                endif

                text = adjustl(text)
                write (*,*) text
            enddo

         end
