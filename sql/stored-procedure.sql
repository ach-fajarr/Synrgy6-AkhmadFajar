SELECT proname
FROM pg_proc
WHERE pronamespace = 'public'::regnamespace;

select * from users u

select p from product p where p.product_name like '%kopi%'

select p from product p join merchant m on p.merchant_id = m.id where m.open = true

--======================================================================--

create or replace procedure add_user(mail varchar, pass varchar, uname varchar)
language plpgsql
as $$
begin
	begin
        INSERT INTO public.users (id, email, "password", username)
        VALUES (gen_random_uuid(), mail, pass, uname);
    exception
        when unique_violation then rollback;
        raise exception 'Email % sudah terdaftar.', mail;
    end;
   	commit;
end;$$

call add_user ('budi', '123', 'siva')

create or replace procedure update_user(id_in UUID, mail varchar, pass varchar, uname varchar)
language plpgsql
as $$
begin
	if mail is not null and mail != '' then
	update public.users set email = mail where id = id_in;
	end if;

	if pass is not null and pass != '' then
	update public.users set "password" = pass where id = id_in;
	end if;

	if uname is not null and uname != '' then
	update public.users set username = uname where id = id_in;
	end if;

	commit;
end;$$

call update_user ('625b2732-8b5c-48c9-ae8f-4bc3d47748a7', '', '123', null)

create or replace procedure get_user_by_id(id_in UUID)
language plpgsql
as $$
begin
	perform * FROM public.users
	where id = id_in;
end;$$

call get_user_by_id('27c5e639-bbdc-405e-83b7-122f8b4d4cc5')

select * from users u

--======================================================================--
select * from merchant m

create or replace procedure update_merchant(id_in UUID, new_name varchar, new_loc varchar)
language plpgsql
as $$
begin
	if new_name is not null and new_name != '' then
	update public.merchant set merchant_name = new_name where id = id_in;
	end if;


	if new_loc is not null and new_loc != '' then
	update public.merchant set merchant_location = new_loc where id = id_in;
	end if;

	commit;
end;$$

call update_merchant ('602eb77e-9e27-4a51-830c-27c6b481b4d7', null , 'surabaya')

--======================================================================--

create or replace function login_user(email_param varchar, password_param varchar)
returns table (
    uid UUID
)
as $$
declare
    user_id UUID;
begin
    SELECT id INTO user_id FROM public.users WHERE email = email_param;

    if user_id is null then
        raise exception 'Email tidak terdaftar' using HINT = 'Email tidak terdaftar';
    else
        SELECT id INTO user_id FROM public.users WHERE email = email_param AND "password" = password_param;

        if user_id is null then
            raise exception 'Password salah' using HINT = 'Password salah';
        else
            uid := user_id;
        end if;
    end if;

    return next;
end;
$$ language plpgsql;

select * from login_user

select * from merchant m

select * from login_user('f', '1a23');

select p from product p join p.merchant_id m where p.merchant_id = '602eb77e-9e27-4a51-830c-27c6b481b4d7'

--=============================================================================================

select * from product p

create or replace procedure update_product(id_in UUID, new_name varchar, new_price bigint)
language plpgsql
as $$
begin
	if new_name is not null and new_name != '' then
	update public.product set product_name = new_name where id = id_in;
	end if;

	if new_price is not null then
	update public.product set price = new_price where id = id_in;
	end if;

	commit;
end;$$

call update_product('92e00e27-d760-4822-b0a3-1f4cf6820db1', '', 30000);
