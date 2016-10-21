--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-10-21 12:03:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 203 (class 1255 OID 17473)
-- Name: first_agg(anyelement, anyelement); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION first_agg(anyelement, anyelement) RETURNS anyelement
    LANGUAGE sql IMMUTABLE STRICT
    AS $_$
        SELECT $1;
$_$;


ALTER FUNCTION public.first_agg(anyelement, anyelement) OWNER TO postgres;

--
-- TOC entry 216 (class 1255 OID 17474)
-- Name: last_agg(anyelement, anyelement); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION last_agg(anyelement, anyelement) RETURNS anyelement
    LANGUAGE sql IMMUTABLE STRICT
    AS $_$
        SELECT $2;
$_$;


ALTER FUNCTION public.last_agg(anyelement, anyelement) OWNER TO postgres;

--
-- TOC entry 217 (class 1255 OID 17475)
-- Name: round(double precision, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION round(double precision, integer) RETURNS numeric
    LANGUAGE sql IMMUTABLE
    AS $_$
    SELECT ROUND($1::numeric,$2);
 $_$;


ALTER FUNCTION public.round(double precision, integer) OWNER TO postgres;

--
-- TOC entry 674 (class 1255 OID 17476)
-- Name: first(anyelement); Type: AGGREGATE; Schema: public; Owner: postgres
--

CREATE AGGREGATE first(anyelement) (
    SFUNC = first_agg,
    STYPE = anyelement
);


ALTER AGGREGATE public.first(anyelement) OWNER TO postgres;

--
-- TOC entry 675 (class 1255 OID 17477)
-- Name: last(anyelement); Type: AGGREGATE; Schema: public; Owner: postgres
--

CREATE AGGREGATE last(anyelement) (
    SFUNC = last_agg,
    STYPE = anyelement
);


ALTER AGGREGATE public.last(anyelement) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 17595)
-- Name: app_params; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE app_params (
    param_name character varying(50),
    param_strvalue character varying(200),
    param_intvalue integer,
    param_lastupdate timestamp(6) without time zone DEFAULT ('now'::text)::date,
    param_label character varying(250),
    param_display character varying(1) DEFAULT 'N'::character varying
);


ALTER TABLE app_params OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 17609)
-- Name: dim_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_category (
    cat_pk integer NOT NULL,
    cat_name character varying(255),
    cat_id character varying(255),
    cat_parent_id character varying(255),
    cat_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    cat_description character varying(4000),
    cat_fullpath character varying(1000)
);


ALTER TABLE dim_category OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 17616)
-- Name: dim_context; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_context (
    con_pk integer NOT NULL,
    con_description character varying(200),
    con_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    con_funckey character varying(500)
);


ALTER TABLE dim_context OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 17623)
-- Name: dim_datasource; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_datasource (
    dso_pk integer NOT NULL,
    dso_connection character varying(250),
    dso_sourcename character varying(250),
    dso_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    dso_funckey character varying(250)
);


ALTER TABLE dim_datasource OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 17603)
-- Name: dim_dqaxis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_dqaxis (
    dqx_pk integer NOT NULL,
    dqx_status integer,
    dqx_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    dqx_name character varying(25),
    dqx_code character varying(10),
    dqx_weight integer DEFAULT 1
);


ALTER TABLE dim_dqaxis OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 17630)
-- Name: dim_glossary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_glossary (
    glo_pk integer,
    glo_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    glo_name character varying(250),
    glo_description character varying(4000),
    glo_id character varying(255)
);


ALTER TABLE dim_glossary OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 17637)
-- Name: dim_job; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_job (
    job_pk integer NOT NULL,
    job_name character varying(250),
    job_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    job_funckey character varying(500)
);


ALTER TABLE dim_job OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 17644)
-- Name: dim_metric; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_metric (
    met_pk integer NOT NULL,
    scg_fk integer,
    met_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    met_name character varying(255),
    met_description character varying(500),
    met_weight integer,
    met_funckey character varying(500),
    mty_fk integer,
    met_path character varying(500)
);


ALTER TABLE dim_metric OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 17651)
-- Name: dim_metrictype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_metrictype (
    mty_pk integer NOT NULL,
    mty_name character varying(255),
    mty_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date
);


ALTER TABLE dim_metrictype OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 17655)
-- Name: dim_origine; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_origine (
    ori_pk integer NOT NULL,
    ori_name character varying(250),
    ori_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    ori_funckey character varying(500)
);


ALTER TABLE dim_origine OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 17662)
-- Name: dim_scorecard; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_scorecard (
    sco_pk integer NOT NULL,
    sco_name character varying(100),
    sco_description character varying(250),
    sco_lastrun timestamp(6) without time zone,
    sco_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    sco_identifier character varying(255),
    sco_funckey character varying(500),
    sco_project character varying(255),
    sco_fullpath character varying(3000)
);


ALTER TABLE dim_scorecard OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 17669)
-- Name: dim_scorecard_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_scorecard_group (
    scg_pk integer NOT NULL,
    sco_fk integer,
    scg_name character varying(250),
    scg_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    scg_funckey character varying(500),
    scg_path character varying(500)
);


ALTER TABLE dim_scorecard_group OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 17676)
-- Name: dim_term; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_term (
    trm_pk integer NOT NULL,
    trm_name character varying(200),
    trm_status character varying(20),
    trm_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    trm_funckey character varying(500),
    trm_description character varying(4000),
    trm_usage character varying(4000),
    trm_example character varying(4000),
    trm_phase character varying(384),
    trm_owner character varying(384),
    trm_steward character varying(384),
    trm_owner_email character varying(500),
    trm_steward_email character varying(500),
    cat_fk integer DEFAULT 0,
    glo_fk integer,
    object_id character varying(250),
    trt_fk integer
);


ALTER TABLE dim_term OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 17684)
-- Name: dim_term_relationship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_term_relationship (
    rel_pk integer,
    rel_name character varying(100),
    rel_description character varying(500)
);


ALTER TABLE dim_term_relationship OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 17690)
-- Name: dim_term_rellinks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_term_rellinks (
    trl_pk integer,
    object_id_source character varying(255),
    object_id_target character varying(255),
    rel_fk integer
);


ALTER TABLE dim_term_rellinks OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 17766)
-- Name: dim_term_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_term_type (
    trt_pk integer,
    trt_name character varying(200),
    trt_description character varying(4000),
    trt_datetime_load timestamp(6) without time zone
);


ALTER TABLE dim_term_type OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 17696)
-- Name: dim_time; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE dim_time (
    tim_pk integer NOT NULL,
    tim_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    tim_calendar_date date,
    tim_day_in_week_name character varying(10),
    tim_month_name character varying(20),
    tim_year_num integer,
    tim_sequence_order integer,
    tim_day_num integer,
    tim_month_num integer
);


ALTER TABLE dim_time OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 17754)
-- Name: fact_governance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE fact_governance (
    frs_pk integer NOT NULL,
    job_fk integer DEFAULT 0 NOT NULL,
    tim_fk integer DEFAULT 0 NOT NULL,
    trm_fk integer DEFAULT 0 NOT NULL,
    ori_fk integer DEFAULT 0 NOT NULL,
    dqx_fk integer DEFAULT 0 NOT NULL,
    met_fk integer DEFAULT 0 NOT NULL,
    con_fk integer DEFAULT 0 NOT NULL,
    frs_valid_rows integer,
    frs_totalrows integer,
    frs_kpi_score double precision,
    frs_datetime_load timestamp(6) without time zone DEFAULT ('now'::text)::date,
    frs_weight integer,
    frs_cost double precision,
    dso_fk integer,
    frs_metric_funckey character varying(250),
    frs_invalid_rows integer
);


ALTER TABLE fact_governance OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 17772)
-- Name: lnd_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_category (
    joyfunckey character varying(250),
    category_name character varying(200),
    category_description character varying(4000),
    category_fullpath character varying(1000),
    category_parent_key character varying(250),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1)
);


ALTER TABLE lnd_category OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 17778)
-- Name: lnd_glossary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_glossary (
    joyfunckey character varying(250),
    glossary_name character varying(200),
    glossary_description character varying(4000),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1)
);


ALTER TABLE lnd_glossary OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 25788)
-- Name: lnd_metric; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_metric (
    joyfunckey character varying(250),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1),
    met_name character varying(255),
    met_description character varying(500),
    met_weight integer,
    frs_valid_rows integer,
    frs_totalrows integer,
    frs_cost double precision,
    scorecard_key character varying(255),
    scorecardgrp_key character varying(255),
    met_score double precision,
    met_type character varying(255),
    met_connection character varying(250),
    met_sourcename character varying(250),
    frs_costunit character varying(128),
    frs_calculation_date timestamp(6) without time zone,
    ori_name character varying(250)
);


ALTER TABLE lnd_metric OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17784)
-- Name: lnd_scorecard; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_scorecard (
    joyfunckey character varying(250),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1),
    sco_name character varying(255),
    sco_description character varying(500),
    sco_lastrun timestamp(6) without time zone
);


ALTER TABLE lnd_scorecard OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 17790)
-- Name: lnd_scorecard_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_scorecard_group (
    joyfunckey character varying(250),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1),
    scgroup_name character varying(255),
    scgroup_description character varying(500),
    scorecard_key character varying(250)
);


ALTER TABLE lnd_scorecard_group OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17796)
-- Name: lnd_term; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_term (
    joyfunckey character varying(250),
    term_name character varying(200),
    term_status character varying(20),
    term_funckey character varying(500),
    term_description character varying(4000),
    term_usage character varying(4000),
    term_example character varying(4000),
    term_phase character varying(384),
    term_owner character varying(384),
    term_steward character varying(384),
    term_owner_email character varying(500),
    term_steward_email character varying(500),
    glossary_key character varying(250),
    category_key character varying(250),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1),
    term_type character varying(255)
);


ALTER TABLE lnd_term OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 17802)
-- Name: lnd_term_relationships; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lnd_term_relationships (
    joyfunckey character varying(250),
    rel_key_term_source character varying(255),
    rel_key_term_target character varying(255),
    rel_name character varying(100),
    rel_description character varying(500),
    joyloaddate timestamp(6) without time zone,
    joystatus character varying(1)
);


ALTER TABLE lnd_term_relationships OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 17700)
-- Name: rel_sc_context; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE rel_sc_context (
    sco_name character varying(100),
    con_description character varying(200),
    scx_description character varying(500),
    scx_pk integer,
    cht_fk integer
);


ALTER TABLE rel_sc_context OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 17706)
-- Name: rel_term_metric; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE rel_term_metric (
    tmd_pk integer NOT NULL,
    tmd_description character varying(500),
    trm_cluster_id integer,
    met_name character varying(500),
    trm_name character varying(500),
    dqx_name character varying(500)
);


ALTER TABLE rel_term_metric OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 17712)
-- Name: src_context; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE src_context (
    con_pk integer NOT NULL,
    con_description character varying(200),
    con_funckey character varying(500)
);


ALTER TABLE src_context OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 17718)
-- Name: src_dqaxis; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE src_dqaxis (
    dqx_code character varying(10),
    dqx_label character varying(250),
    dqx_description character varying(4000),
    dqx_status character varying(1),
    dqx_pk integer NOT NULL,
    dqx_weight integer
);


ALTER TABLE src_dqaxis OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 17724)
-- Name: src_termtype; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE src_termtype (
    gio_pk integer,
    gio_termtype_name character varying(250),
    gio_icon_pathname character varying(500)
);


ALTER TABLE src_termtype OWNER TO postgres;

--
-- TOC entry 2052 (class 1259 OID 17730)
-- Name: dim_category_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_category_pk ON dim_category USING btree (cat_pk);


--
-- TOC entry 2053 (class 1259 OID 17731)
-- Name: dim_context_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_context_pk ON dim_context USING btree (con_pk);


--
-- TOC entry 2054 (class 1259 OID 17732)
-- Name: dim_datasource_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_datasource_pk ON dim_datasource USING btree (dso_pk);


--
-- TOC entry 2051 (class 1259 OID 17608)
-- Name: dim_dqdimension_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_dqdimension_pk ON dim_dqaxis USING btree (dqx_pk);


--
-- TOC entry 2055 (class 1259 OID 17733)
-- Name: dim_glossary_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_glossary_pk ON dim_glossary USING btree (glo_pk);


--
-- TOC entry 2056 (class 1259 OID 17734)
-- Name: dim_job_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_job_pk ON dim_job USING btree (job_pk);


--
-- TOC entry 2058 (class 1259 OID 17735)
-- Name: dim_metrictype_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_metrictype_pk ON dim_metrictype USING btree (mty_pk);


--
-- TOC entry 2059 (class 1259 OID 17736)
-- Name: dim_origine_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_origine_pk ON dim_origine USING btree (ori_pk);


--
-- TOC entry 2057 (class 1259 OID 17737)
-- Name: dim_rules_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_rules_pk ON dim_metric USING btree (met_pk);


--
-- TOC entry 2060 (class 1259 OID 17738)
-- Name: dim_scorecard_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_scorecard_pk ON dim_scorecard USING btree (sco_pk);


--
-- TOC entry 2062 (class 1259 OID 17739)
-- Name: dim_term_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_term_pk ON dim_term USING btree (trm_pk);


--
-- TOC entry 2063 (class 1259 OID 17740)
-- Name: dim_term_relationship_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_term_relationship_pk ON dim_term_relationship USING btree (rel_pk);


--
-- TOC entry 2064 (class 1259 OID 17745)
-- Name: dim_time_index5; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX dim_time_index5 ON dim_time USING btree (tim_year_num);


--
-- TOC entry 2065 (class 1259 OID 17750)
-- Name: dim_time_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX dim_time_pk ON dim_time USING btree (tim_pk);


--
-- TOC entry 2067 (class 1259 OID 17765)
-- Name: fact_gvresult_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX fact_gvresult_pk ON fact_governance USING btree (frs_pk);


--
-- TOC entry 2066 (class 1259 OID 17751)
-- Name: src_dqaxis_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX src_dqaxis_pk ON src_dqaxis USING btree (dqx_pk);


--
-- TOC entry 2061 (class 1259 OID 17752)
-- Name: table_11_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX table_11_pk ON dim_scorecard_group USING btree (scg_pk);


--
-- TOC entry 2068 (class 1259 OID 17808)
-- Name: trt_pk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX trt_pk ON dim_term_type USING btree (trt_pk);


--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-10-21 12:03:41

--
-- PostgreSQL database dump complete
--

